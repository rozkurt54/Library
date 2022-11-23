package com.library.Library.dataAccess;

import com.library.Library.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {


    Optional<Image> findByFileName(String name);

}
