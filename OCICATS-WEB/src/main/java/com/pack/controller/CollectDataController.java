package com.pack.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pack.domain.Query;
import com.pack.service.SearchService;

@Controller
@RequestMapping("/collecting")
public class CollectDataController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping
	public String list() {
		
		return "search";
	}

	@RequestMapping(value = "/searchSubmmit", method = RequestMethod.GET)
	public String goToCart() {
		
		return "redirect:/topics";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchPage(Model model) {
		Query newQuery = new Query();
		model.addAttribute("newQuery", newQuery);
		return "search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String processingSearch(@ModelAttribute("newQuery") Query newQuery, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		String q = newQuery.getQuery();
		String startdate = newQuery.getStartDate();
		String enddate = newQuery.getEndDate();
		try {
			searchService.StoreFileToResources(q, startdate, enddate, path);
			System.out.println(q);
			System.out.println(startdate);
			System.out.println(enddate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/collecting/searchSubmmit";
	}
	
	
	
	/*
	 * 
	 * @RequestMapping("/{category}") public String getProductsByCategory(Model
	 * model,
	 * 
	 * @PathVariable String category) { List<Product> products =
	 * productService.getProductsByCategory(category);
	 * 
	 * if(products == null || products.isEmpty()) { throw new
	 * NoProductsFoundUnderCategoryException(); }
	 * 
	 * model.addAttribute("products", products); return "products"; }
	 * 
	 * 
	 * @RequestMapping("/filter/{ByCriteria}") public String
	 * getProductsByFilter(Model model,
	 * 
	 * @MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> map) {
	 * model.addAttribute("products", productService.getProductsByFilter(map));
	 * return "products"; }
	 * 
	 * 
	 * @RequestMapping("/product") public String
	 * getProductById(@RequestParam("id") String productId, Model model) {
	 * model.addAttribute("product", productService.getProdcutById(productId));
	 * return "product"; }
	 * 
	 * 
	 * @RequestMapping("/tablet/{price}") public String
	 * filterProducts(@MatrixVariable(pathVar = "price") Map<String,
	 * List<String>> map,
	 * 
	 * @RequestParam("manufacturer") String manufacturer, Model model) {
	 * 
	 * Set<Product> productsByMatrixVariable =
	 * productService.getProductsByPriceFilter(map); Set<Product>
	 * productsByManufactor = new
	 * HashSet<>(productService.getProductsByManufacturer(manufacturer));
	 * 
	 * productsByMatrixVariable.retainAll(productsByManufactor);
	 * 
	 * model.addAttribute("products", productsByMatrixVariable); return
	 * "products"; }
	 * 
	 * 
	 * @RequestMapping(value = "/add", method = RequestMethod.GET) public String
	 * getAddNewProdcutForm(Model model) { Product newProduct = new Product();
	 * model.addAttribute("newProduct", newProduct); return "addProduct"; }
	 * 
	 * 
	 * @RequestMapping(value = "/add", method = RequestMethod.POST) public
	 * String processAddNewProductForm(@ModelAttribute("newProduct") Product
	 * newProduct, HttpServletRequest request) {
	 * 
	 * if(result.hasErrors()) { return "addProduct"; }
	 * 
	 * String[] suppressedField = result.getSuppressedFields();
	 * if(suppressedField.length > 0) { throw new RuntimeException(
	 * "Attempting to bind disallowed fields: " +
	 * StringUtils.arrayToCommaDelimitedString(suppressedField)); }
	 * 
	 * MultipartFile productImage = newProduct.getProductImage(); String
	 * rootDirectory =
	 * request.getSession().getServletContext().getRealPath("/"); String path =
	 * rootDirectory + "resources/images/" + newProduct.getProductId() + ".png";
	 * 
	 * if(productImage != null && !productImage.isEmpty()) { try {
	 * productImage.transferTo(new File(path)); }catch(Exception e) { throw new
	 * RuntimeException("Product Image saving failed", e); } }
	 * 
	 * productService.addProduct(newProduct); return "redirect:/products" ; }
	 * 
	 * 
	 * @InitBinder public void initializeBinder(WebDataBinder binder) {
	 * binder.setDisallowedFields("unitsInOrder", "discontinued");
	 * binder.setAllowedFields("productId", "name", "unitPrice", "description",
	 * "manufacturer", "category", "unitsInStock","productImage"); }
	 * 
	 * @ExceptionHandler(ProductNotFoundException.class) public ModelAndView
	 * handleError(HttpServletRequest req, ProductNotFoundException exception) {
	 * ModelAndView model = new ModelAndView();
	 * model.addObject("invalidProductId", exception.getProductId());
	 * model.addObject("exception", exception); model.addObject("url",
	 * req.getRequestURI() + "?" + req.getQueryString());
	 * model.setViewName("productNotFound"); return model; }
	 */

}
