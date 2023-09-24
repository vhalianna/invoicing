package com.tuempresa.invoicing.actions;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.logging.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.session.*;
import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.invoicing.model.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;


public class ProductReportAction extends JasperReportBaseAction implements IChainAction {
	private static Log log = LogFactory.getLog(ProductReportAction.class);
	
	private Product product;
	private boolean newAfter = false;

	@Override
	public void execute() throws Exception {
		super.execute();
		addMessage("ProductReportAction.printOK");
	}
	
	public Map getParameters() throws Exception  {
		Messages errors = MapFacade.validate("Product", getView().getValues());
		if (errors.contains()) throw new ValidationException(errors);
		Map parameters = new HashMap();				
		Product p = getProduct();
		parameters.put("title", "Producto");
		parameters.put("number", p.getNumber());
		parameters.put("description", p.getDescription());
		parameters.put("category", p.getCategory().getDescription());
		parameters.put("price", p.getPrice());
		parameters.put("remarks", p.getRemarks());
		parameters.put("photos", getImage(getProduct().getPhotos()));
		return parameters;
	}
	
	protected JRDataSource getDataSource() throws Exception {
		Collection<Product> products = new ArrayList<Product>();
		products.add(getProduct());
		return new JRBeanCollectionDataSource(products);
	}
	
	protected String getJRXML() {
		return "Product.jrxml"; // In this way it's readed from classpath
		// return "/home/java/temporal/Product.jrxml"; // In this way it's readed from file system
	}
	
	private Product getProduct() throws Exception {
		if (product == null) {
			int number = getView().getValueInt("number");
			product = Product.findByNumber(number);
		}
		return product;
	}

	public String getNextAction() throws Exception {
		return newAfter ? "CRUD.new" : null;
	}

	public boolean isNewAfter() {
		return newAfter;
	}

	public void setNewAfter(boolean newAfter) {
		this.newAfter = newAfter;
	}
	
	private byte[] getImage(String galleryImage) {
		Query query = XPersistence.getManager().createQuery("from GalleryImage where galleryOid=:galleryOid");
		query.setParameter("galleryOid", galleryImage);
		List images = query.getResultList();
		GalleryImage gi = (GalleryImage) images.get(0);
        return gi.getImage();
	}
}
