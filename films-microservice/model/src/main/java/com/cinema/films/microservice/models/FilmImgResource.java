package com.cinema.films.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "film_img_resources")
public class FilmImgResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "File name can't be null!")
    @Size(min = 32, max = 32, message = "File name must contain 32 characters!")
    @Column(name = "fileName", length = 32, nullable = false, unique = true)
    private String fileName;

    @NotNull(message = "File extension can't be null!")
    @Size(min = 3, message = "File extension must contain at least 3 characters!")
    @Column(name = "fileExtension", length = 16, nullable = false, unique = true)
    private String fileExtension;

    @NotNull(message = "File resolution can't be null!")
    @Min(value = 0, message = "File resolution on X axis can't be lesser than pixels!")
    @Column(name = "resolutionX", nullable = false, unique = true)
    private Integer resolutionX;

    @NotNull(message = "File resolution can't be null!")
    @Min(value = 0, message = "File resolution on Y axis can't be lesser than pixels!")
    @Column(name = "resolutionY", nullable = false, unique = true)
    private Integer resolutionY;

    @NotNull(message = "Image size can't be null!")
    @Min(value = 0, message = "Image size can't be lesser than 0 byte!")
    @Column(name = "size", nullable = false)
    private Long size;

    @NotNull(message = "Image creation date can't be null!")
    @Column(name = "creationTime", nullable = false)
    private Date creationTime;

    @NotNull(message = "Image updating date can't be null!")
    @Column(name = "updatingTime", nullable = false)
    private Date updatingTime;

}
