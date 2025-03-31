package com.shilov.spring_reservation;

import com.shilov.spring_reservation.common.config.SpringConfig;
import com.shilov.spring_reservation.common.enums.SpaceType;
import com.shilov.spring_reservation.common.exceptions.RepositoryException;
import com.shilov.spring_reservation.models.Space;
import com.shilov.spring_reservation.repository.SpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class SpaceRepositoryTest {

    private SpaceRepository spaceRepository;

    @BeforeEach
    void setUp() throws RepositoryException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        spaceRepository = applicationContext.getBean(SpaceRepository.class);
        spaceRepository.deleteAllSpaces();
    }

    @Test
    void getAllSpaces_shouldReturnSpacesWhenFound() throws RepositoryException {
        List<Space> spacesToAdd = new ArrayList<>(){};
        spacesToAdd.add(new Space());
        spacesToAdd.add(new Space());
        for (Space space : spacesToAdd) {
            Long spaceId = spaceRepository.addSpace(space);
            space.setId(spaceId);
        }

        List<Space> result = spaceRepository.getAllSpaces();

        assertEquals(spacesToAdd, result);
    }

    @Test
    void getAllSpaces_shouldReturnEmptyListWhenNotFound() throws RepositoryException {
        List<Space> result = spaceRepository.getAllSpaces();

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideSpaces")
    void getSpaceById_shouldReturnSpaceWhenFound(Space space) throws RepositoryException {
        Long spaceId = spaceRepository.addSpace(space);
        Optional<Space> result = spaceRepository.getSpaceById(spaceId);
        assertTrue(result.isPresent());
    }

    private static Stream<Space> provideSpaces() {
        return Stream.of(new Space(), new Space(), new Space());
    }

    @Test
    void getSpaceById_shouldReturnEmptyOptionalWhenNotFound() throws RepositoryException {
        Optional<Space> result = spaceRepository.getSpaceById(-2L);

        assertTrue(result.isEmpty());
    }

    @Test
    void addSpace_shouldAddSpaceToList() throws RepositoryException {
        Space space = new Space(SpaceType.ROOM, 30);

        spaceRepository.addSpace(space);

        assertFalse(spaceRepository.getAllSpaces().isEmpty());
    }

    @Test
    void deleteSpace_shouldRemoveSpaceWhenFound() throws RepositoryException {
        Space spaceToAdd = new Space();
        Long spaceId = spaceRepository.addSpace(spaceToAdd);

        spaceRepository.deleteSpace(spaceId);

        assertTrue(spaceRepository.getSpaceById(spaceId).isEmpty());
    }

    @Test
    void deleteSpace_shouldThrowRepositoryExceptionWhenNotFound() {
        assertThrows(RepositoryException.class, () -> spaceRepository.deleteSpace(-4L));
    }

    @Test
    void updateSpace_shouldUpdateSpaceWhenFound() throws RepositoryException {
        Space initSpace = new Space();
        initSpace.setType(SpaceType.ROOM);
        Long spaceId = spaceRepository.addSpace(initSpace);
        Space spaceBeforeUpdate = spaceRepository.getSpaceById(spaceId).get();
        initSpace.setType(SpaceType.PRIVATE);

        spaceRepository.updateSpace(spaceId, initSpace);
        Optional<Space> optionalAfterUpdate = spaceRepository.getSpaceById(spaceId);

        assertTrue(optionalAfterUpdate.isPresent());
        assertNotEquals(spaceBeforeUpdate, optionalAfterUpdate.get());
    }

    @Test
    void updateSpace_shouldThrowRepositoryExceptionWhenSpaceNotFound() {
        assertThrows(RepositoryException.class, () -> spaceRepository.updateSpace(-5L, new Space()));
    }
}
