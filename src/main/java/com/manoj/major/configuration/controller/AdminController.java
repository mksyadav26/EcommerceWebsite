package com.manoj.major.configuration.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.manoj.major.dto.ProductDto;
import com.manoj.major.model.Category;
import com.manoj.major.model.Product;
import com.manoj.major.service.CategoryService;
import com.manoj.major.service.ProductService;
@Controller
public class AdminController {
	
	public String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@GetMapping("/admin")
	public String adminHome() {
		
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String showCategory(Model model) {
	model.addAttribute("categories", categoryService.getAllCategory());
		return "categories"; 
	}
	
    @GetMapping("/admin/categories/add")
    	public String addCategory(Model model) {
    	model.addAttribute("category", new Category());
    	return "categoriesAdd";
    }
    
    @PostMapping("/admin/categories/add")
    public String postAddCategory(@ModelAttribute("category") Category category) {
    	categoryService.addCategory(category); 
		return  "redirect:/admin/categories";
    	
    }
    
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id ) {
    	categoryService.removeCategoryById(id);
    	return  "redirect:/admin/categories";
    }
    
    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id,Model model ) {
    	Optional<Category> category= categoryService.updateCategoryById(id);
    	if(category.isPresent()) {
    		model.addAttribute("category", category.get());
    		return  "categoriesAdd";
    	}else return "404";
    	
    }
    
    //product Section
   @GetMapping("/admin/products")
   public String showProduct(Model model) {
	   model.addAttribute("products", productService.getAllProduct());
	   return "products";
   }
    	 
   @GetMapping("/admin/products/add")
   public String addProduct(Model model) 
   {
	   model.addAttribute("productDTO", new ProductDto());
	   model.addAttribute("categories", categoryService.getAllCategory());
	   return "productsAdd";
   }

   
   @PostMapping("/admin/products/add")
   public String postProduct(@ModelAttribute("productDto") ProductDto productDto ,
		   @RequestParam("productImage")MultipartFile file,
		   @RequestParam("imgName")String imgName) throws IOException
   { 
	   Product product = new Product();
	   product.setId(productDto.getId());
	   product.setName(productDto.getName());
	   product.setDescription(productDto.getDescription());
	   product.setPrice(productDto.getPrice());
	   product.setWeight(productDto.getWeight());
	  
	   product.setCategory(categoryService.updateCategoryById(productDto.getCategoryId()).get());
	   String imgUUID;
	   if(!file.isEmpty()) {
		   imgUUID= file.getOriginalFilename();
		   Path fileNameandPath = Paths.get(uploadDir, imgUUID);
		   Files.write(fileNameandPath, file.getBytes());
	   }
	   else {
		   imgUUID=imgName;
	   }
	   
	   product.setImageName(imgUUID);
	   
	   productService.addProduct(product);
	
	  return  "redirect:/admin/products";
   }
   
   @GetMapping("/admin/product/delete/{id}")
   public String deleteProduct(@PathVariable long id) {
	   productService.deleteProductById(id);
	   return "redirect:/admin/products";
   }
 
   @GetMapping("/admin/product/update/{id}")
   public String updateProduct(@PathVariable long id,Model model) {
	   Product product = productService.getProductbyId(id).get();
	   ProductDto productDto = new ProductDto();
	   productDto.setId(product.getId());
	   productDto.setName(product.getName());
	   productDto.setCategoryId(product.getCategory().getId());
	   productDto.setDescription(product.getDescription());
	   productDto.setPrice(product.getPrice());
	   productDto.setWeight(product.getWeight());
	   productDto.setImageName(product.getImageName());
	   model.addAttribute("categories", categoryService.getAllCategory());
	   model.addAttribute("productDTO", productDto);
	   
	  return "productsAdd";
   }
   
}
    	 
 	 



