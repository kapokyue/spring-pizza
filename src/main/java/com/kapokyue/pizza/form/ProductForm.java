package com.kapokyue.pizza.form;

import com.kapokyue.pizza.model.Category;
import com.kapokyue.pizza.model.Product;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

@Data
public class ProductForm
{
  @NotNull
  @Min( 0 )
  long price;

  @NotNull
  Category category;

  @NotBlank
  String name, description;

  MultipartFile imgFile;

  String imgUrl;

  public Product makeProduct( ServletContext context ) throws IOException
  {
    Product p = new Product();
    p.setName( name );
    p.setPrice( price );
    p.setDescription( description );

    p.setCategory( category );

    if ( imgUrl != null && !imgUrl.isEmpty() ) {
      p.setImg( imgUrl );
    } else if ( imgFile != null ) {
      String realPath = context.getRealPath( "/" );
      if ( realPath == null ) {
        throw new UnsupportedOperationException( "Server don't support upload file to public directory" );
      } else {
        if ( imgFile.isEmpty() ) {
          throw new IllegalStateException( "No file uploaded" );
        } else {
          String ext = imgFile.getOriginalFilename()
                              .substring(
                                  imgFile.getOriginalFilename().lastIndexOf( '.' ) );
          String fileUri = "/img/" + imgFile.hashCode() + ext;
          String fullName = realPath + fileUri;
          File f = new File( fullName );
          imgFile.transferTo( f );
          p.setImg( fileUri );
        }
      }
    }
    return p;
  }
}
