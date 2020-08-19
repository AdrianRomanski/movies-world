package adrianromanski.movies.services.image;

import adrianromanski.movies.mapper.base_entity.CategoryMapper;
import adrianromanski.movies.model.base_entity.CategoryDTO;
import adrianromanski.movies.repositories.base_entity.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceCategory implements ImageService<CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ImageServiceCategory(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void saveImageFile(CategoryDTO categoryDTO, MultipartFile file) throws IOException {
        var category = categoryMapper.categoryDTOToCategory(categoryDTO);
        var byteObjects = new Byte[file.getBytes().length];

        var i = 0;

        for(byte b: file.getBytes()) {
            byteObjects[i++] = b;
        }
        category.setImage(byteObjects);
        categoryRepository.save(category);
    }
}
