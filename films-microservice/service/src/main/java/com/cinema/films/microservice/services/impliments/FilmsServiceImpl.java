package com.cinema.films.microservice.services.impliments;

import com.cinema.common.utils.generators.StringGenerator;
import com.cinema.films.microservice.domains.FilmDto;
import com.cinema.films.microservice.domains.FilmImgResourceDto;
import com.cinema.films.microservice.exceptions.services.films.FilmAlreadyExistException;
import com.cinema.films.microservice.exceptions.services.films.FilmCreationException;
import com.cinema.films.microservice.exceptions.services.films.FilmNotFoundException;
import com.cinema.films.microservice.exceptions.services.films.FilmUpdatingException;
import com.cinema.films.microservice.exceptions.services.films.img.FilmImgExtensionException;
import com.cinema.films.microservice.exceptions.services.films.img.FilmImgSavingException;
import com.cinema.films.microservice.exceptions.services.films.img.resources.FileImgWrongExtensionException;
import com.cinema.films.microservice.models.Film;
import com.cinema.films.microservice.models.FilmImgResource;
import com.cinema.films.microservice.repositories.FilmImgResourcesRepository;
import com.cinema.films.microservice.repositories.FilmsRepository;
import com.cinema.films.microservice.services.FilmsService;
import com.cinema.films.microservice.services.mappers.FilmImgResourceMapper;
import com.cinema.films.microservice.services.mappers.FilmMapper;
import com.cinema.films.microservice.storages.ResourcesStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.tika.Tika;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private FilmImgResourcesRepository filmImgResourcesRepository;

    @Autowired
    private ResourcesStorage resourcesStorage;

    @Value("${films.page.size}")
    private Integer FILMS_PAGE_SIZE;

    @Override
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmsRepository.findAll();
        log.info("{} films has been found.", films.size());
        return films.stream().map(FilmMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<FilmDto> getPageOfFilms(Integer page) {
        Pageable pageable = PageRequest.of(page, FILMS_PAGE_SIZE);
        List<Film> films = filmsRepository.findAll(pageable).getContent();
        log.info("{} films has been found.", films.size());
        return films.stream().map(FilmMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @Override
    public FilmDto getFilm(Long id) {
        if (filmsRepository.existsById(id)) {
            log.info("Film with id '{}' has been found.", id);
            return FilmMapper.INSTANCE.mapToDto(filmsRepository.getReferenceById(id));
        } else {
            log.warn("Film with id '{}' not found!", id);
            throw new FilmNotFoundException(String.format("Film with id '%d' not found!", id));
        }
    }

    @Override
    public FilmDto saveFilm(@Valid Film film, MultipartFile imgFile) {
        if (!filmsRepository.existsByName(film.getName())) {
            try {
                FilmImgResource filmImgResource = saveImg(imgFile);
                film.setImgResource(filmImgResource);
                Film savedFilm = filmsRepository.save(film);
                log.info("New film '{}' has been saved.", savedFilm.getName());
                return FilmMapper.INSTANCE.mapToDto(savedFilm);
            } catch (Exception e) {
                log.error("Can't create a new film! " + e.getMessage());
                throw new FilmCreationException("Can't create a new film! " + e.getMessage());
            }
        } else {
            log.warn("Film with name '{}' already exist!", film.getName());
            throw new FilmAlreadyExistException(String.format("Film with name '%s' already exist!", film.getName()));
        }
    }

    @Override
    public FilmDto updateFilm(@Valid Film film, MultipartFile imgFile) {
        if (filmsRepository.existsById(film.getId())) {
            if (!filmsRepository.existsByName(film.getName())) {
                try {
                    FilmImgResource newResources = saveImg(imgFile);
                    Film oldFilm = filmsRepository.getReferenceById(film.getId());
                    FilmImgResource oldResources = oldFilm.getImgResource();

                    film.setImgResource(newResources);
                    film = filmsRepository.save(film);

                    resourcesStorage.deleteFileByName(oldResources.getFileName());
                    filmImgResourcesRepository.delete(oldResources);

                    return FilmMapper.INSTANCE.mapToDto(film);
                } catch (Exception e) {
                    log.error("Can't update a film! " + e.getMessage());
                    throw new FilmUpdatingException("Can't update a film! " + e.getMessage());
                }
            } else {
                log.warn("Film with name '{}' already exist!", film.getName());
                throw new FilmAlreadyExistException(String.format("Film with name '%s' already exist!", film.getName()));
            }
        } else {
            log.warn("Film with id '{}' not found!", film.getId());
            throw new FilmNotFoundException(String.format("Film with id '%d' not found!", film.getId()));
        }
    }

    @Override
    public FilmImgResourceDto getFilmMetadata(Long id) {
        if (filmsRepository.existsById(id)) {
            Film film = filmsRepository.getReferenceById(id);
            log.info("Film with id '{}' has been got.", id);
            return FilmImgResourceMapper.INSTANCE.mapToDto(film.getImgResource());
        } else {
            log.warn("Film with id '{}' not found!", id);
            throw new FilmNotFoundException(String.format("Film with id '%d' not found!", id));
        }
    }

    @Override
    public Long deleteFilm(Long id) {
        if (filmsRepository.existsById(id)) {
            Film film = filmsRepository.getReferenceById(id);
            FilmImgResource resource = film.getImgResource();
            resourcesStorage.deleteFileByName(resource.getFileName());
            log.info("Img for the film with id '{}' has been deleted.", id);
            filmImgResourcesRepository.delete(resource);
            log.info("Img resources for the film with id '{}' has been deleted.", id);
            filmsRepository.delete(film);
            log.info("Film with id '{}' has been deleted.", id);
            return id;
        } else {
            log.warn("Film with id '{}' not found!", id);
            throw new FilmNotFoundException(String.format("Film with id '%d' not found!", id));
        }    }

    private FilmImgResource saveImg(MultipartFile file) {
        if (fileExtensionCheck(file)) {
            try {
                FilmImgResource filmImgResource = createImgResource(file);
                if (resourcesStorage.saveFile(filmImgResource.getFileName(), file.getInputStream(), file.getSize(), file.getContentType())) {
                    log.info("Film img '{}' has been saved in resource storage.", filmImgResource.getFileName());
                    filmImgResource = filmImgResourcesRepository.save(filmImgResource);
                    log.info("Film metadata '{}' has been saved.", filmImgResource.toString());
                    return filmImgResource;
                } else {
                    log.warn("Can't save img file! Resource storage exception!");
                    throw new FilmImgSavingException("Can't save img file! Resource storage exception!");
                }
            } catch (Exception e) {
                log.error("Can't save img file! " + e.getMessage());
                throw new FilmImgSavingException("Can't save img file! " + e.getMessage());
            }
        } else {
            log.warn("Can't save img file! Wrong file extension!");
            throw new FileImgWrongExtensionException("Can't save img file! Wrong file extension!");
        }
    }

    @NotNull
    private FilmImgResource createImgResource(MultipartFile file) throws IOException {
        FilmImgResource filmImgResource = new FilmImgResource();
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        Long fileSize = file.getSize();
        String suffix = FileNameUtils.getExtension(file.getOriginalFilename());
        StringBuilder newFileName = new StringBuilder();
        newFileName.append(StringGenerator.generateRandomString(32));
        newFileName.append(".");
        newFileName.append(suffix);

        filmImgResource.setFileName(newFileName.toString());
        filmImgResource.setFileExtension(suffix);
        filmImgResource.setSize(fileSize);
        filmImgResource.setResolutionX(bufferedImage.getWidth());
        filmImgResource.setResolutionY(bufferedImage.getHeight());
        filmImgResource.setCreationTime(new Date());
        filmImgResource.setUpdatingTime(new Date());
        return filmImgResource;
    }

    private Boolean fileExtensionCheck(MultipartFile file) {
        try {
            Tika tika = new Tika();
            switch (tika.detect(file.getInputStream())) {
                case "image/jpeg" :
                case "image/jpg" :
                case "image/png" :
                    return true;
                default: return false;
            }
        } catch (Exception e) {
            log.error("Can't save img file! " + e.getMessage());
            throw new FilmImgExtensionException("Can't save img file! " + e.getMessage());
        }
    }

}
