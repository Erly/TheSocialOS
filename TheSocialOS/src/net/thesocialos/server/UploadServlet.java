package net.thesocialos.server;

import static gwtupload.shared.UConsts.PARAM_SHOW;
import static gwtupload.shared.UConsts.TAG_ERROR;
import static gwtupload.shared.UConsts.TAG_FINISHED;
import gwtupload.server.AbstractUploadListener;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.BlobstoreFileItemFactory;
import gwtupload.server.gae.BlobstoreFileItemFactory.BlobstoreFileItem;
import gwtupload.server.gae.MemCacheUploadListener;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thesocialos.shared.model.ImageUpload;
import net.thesocialos.shared.model.User;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class UploadServlet extends UploadAction {
	
	protected static BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	protected static DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	protected static BlobInfoFactory blobInfoFactory = new BlobInfoFactory(datastoreService);
	
	private static final long serialVersionUID = -2569300604226532811L;
	
	// See constrain 1
	private String servletPath = "/upload";
	
	@Override
	public void checkRequest(HttpServletRequest request) {
		logger.debug("BLOB-STORE-SERVLET: (" + request.getSession().getId() + ") procesing a request with size: "
				+ request.getContentLength() + " bytes.");
	}
	
	@Override
	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parameter = request.getParameter(PARAM_SHOW);
		FileItem item = findFileItem(getSessionFileItems(request), parameter);
		if (item != null) {
			BlobInfo i = blobInfoFactory.loadBlobInfo(((BlobstoreFileItem) item).getKey());
			if (i != null) {
				logger.debug("BLOB-STORE-SERVLET: (" + request.getSession().getId() + ") getUploadedFile: " + parameter
						+ " serving blobstore: " + i);
				blobstoreService.serve(((BlobstoreFileItem) item).getKey(), response);
			} else
				logger.error("BLOB-STORE-SERVLET: (" + request.getSession().getId() + ") getUploadedFile: " + parameter
						+ " file isn't in blobstore.");
		} else {
			logger.info("BLOB-STORE-SERVLET: (" + request.getSession().getId() + ") getUploadedFile: " + parameter
					+ " file isn't in session.");
			renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
		}
	}
	
	@Override
	public boolean isAppEngine() {
		return true;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.info("Initializing Blobstore servlet.");
		uploadDelay = 0;
		useBlobstore = true;
		logger.info("BLOB-STORE-SERVLET: init: maxSize=" + maxSize + ", slowUploads=" + uploadDelay + ", isAppEngine="
				+ isAppEngine() + ", useBlobstore=" + useBlobstore);
	}
	
	@Override
	protected final AbstractUploadListener createNewListener(HttpServletRequest request) {
		return new MemCacheUploadListener(uploadDelay, request.getContentLength());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getParameter("blob-key") != null) blobstoreService.serve(
				new BlobKey(request.getParameter("blob-key")), response);
		else if (request.getParameter("redirect") != null) {
			perThreadRequest.set(request);
			String ret = TAG_ERROR;
			Map<String, String> stat = getUploadStatus(request, null, null);
			List<FileItem> items = getSessionFileItems(request);
			int nitems = 0;
			if (items != null) {
				nitems = items.size();
				for (FileItem item : getSessionFileItems(request)) {
					BlobKey k = ((BlobstoreFileItem) item).getKey();
					BlobInfo i = blobInfoFactory.loadBlobInfo(k);
					if (i != null) {
						stat.put("ctype", i.getContentType() != null ? i.getContentType() : "unknown");
						stat.put("size", "" + i.getSize());
						stat.put("name", "" + i.getFilename());
					}
					stat.put("blobkey", k.getKeyString());
					stat.put("message", k.getKeyString());
				}
			}
			stat.put(TAG_FINISHED, "ok");
			ret = statusToString(stat);
			finish(request);
			logger.debug("BLOB-STORE-SERVLET: (" + request.getSession().getId() + ") redirect nitems=" + nitems + "\n"
					+ ret);
			renderXmlResponse(request, response, ret, true);
			perThreadRequest.set(null);
		} else
			super.doGet(request, response);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		String error = null;
		String message = null;
		BlobKey blobKey = null;
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		if (blobs != null && blobs.size() > 0) {
			
			List<BlobstoreFileItem> items = new Vector<BlobstoreFileItem>();
			
			for (Entry<String, List<BlobKey>> e : blobs.entrySet()) {
				BlobstoreFileItem i = new BlobstoreFileItem(e.getKey(), "unknown", false, "");
				/*
				 * logger.info("BLOB-STORE-SERVLET: received file: " + e.getKey() + " " +
				 * e.getValue().get(0).getKeyString());
				 */
				
				items.add(i);
				
			}
			String userEmail = UserHelper.getUserHttpSession(request.getSession());
			Objectify ofy = ObjectifyService.begin();
			User user = UserHelper.getUserWithEmail(userEmail, ofy);
			if (user == null) return;
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			String imageUrl = imagesService.getServingUrl(blobKey);
			
			ImageUpload uploadedImage = new ImageUpload();
			uploadedImage.setKey(blobKey.getKeyString());
			uploadedImage.setCreatedAt(new Date());
			uploadedImage.setServingUrl(imageUrl);
			if (request.getParameter("media").isEmpty()) {
				
				user.setAvatar(uploadedImage.getServingUrl());
				ofy.put(uploadedImage);
				ofy.put(user);
			} else {
				/*
				 * Si el parametro media no esta vacio. busca: facebook;picassa; donde ";" es el separador que le he
				 * puesto
				 */
				items.get(0).getInputStream(); // creo que es el archivo
				items.get(0).delete(); // borrar el blob de la blobstore
				
			}
			
		}
		
		/*
		 * List<FileItem> items = new Vector<FileItem>(); for (Entry<String, BlobKey> e : blobs.entrySet()) {
		 * BlobstoreFileItem i = new BlobstoreFileItem(e.getKey(), "unknown", false, "");
		 * logger.info("BLOB-STORE-SERVLET: received file: " + e.getKey() + " " + e.getValue().getKeyString());
		 * i.setKey(e.getValue()); items.add(i); } logger.info("BLOB-STORE-SERVLET: putting in sesssion elements -> " +
		 * items.size()); request.getSession().setAttribute(SESSION_FILES, items);
		 */
		else
			error = getMessage("no_data");
		
		try {
			message = executeAction(request, getSessionFileItems(request));
		} catch (UploadActionException e) {
			logger.info("ExecuteUploadActionException: " + e);
			error = e.getMessage();
		}
		
	}
	
	@Override
	protected String getBlobstorePath(HttpServletRequest request) {
		String ret = blobstoreService.createUploadUrl(servletPath);
		ret = ret.replaceAll("^https*://[^/]+", "");
		logger.info("BLOB-STORE-SERVLET: generated new upload-url -> " + servletPath + " : " + ret);
		return ret;
	}
	
	@Override
	protected final AbstractUploadListener getCurrentListener(HttpServletRequest request) {
		return MemCacheUploadListener.current(request.getSession().getId());
	}
	
	@Override
	protected final FileItemFactory getFileItemFactory(int requestSize) {
		return new BlobstoreFileItemFactory();
	}
}