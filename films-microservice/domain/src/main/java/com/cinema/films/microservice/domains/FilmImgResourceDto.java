package com.cinema.films.microservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmImgResourceDto {

    private Long id;
    private String fileName;
    private String fileExtension;
    private Integer resolutionX;
    private Integer resolutionY;
    private Long size;
    private Date creationTime;
    private Date updatingTime;

}
