package org.openmrs.sync.core.mapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.sync.core.entity.Patient;
import org.openmrs.sync.core.entity.light.ConceptLight;
import org.openmrs.sync.core.entity.light.UserLight;
import org.openmrs.sync.core.model.PatientModel;
import org.openmrs.sync.core.service.SimpleService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class PatientMapperTest {

    @Mock
    private SimpleService<UserLight> userService;

    @Mock
    private SimpleService<ConceptLight> conceptService;

    @InjectMocks
    private PatientMapperImpl mapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void entityToModel() {
        // Given
        Patient ety = getPatientEty();

        // When
        PatientModel result = mapper.entityToModel(ety);

        // Then
        assertResult(ety, result);
    }

    @Test
    public void modelToEntity() {
        // Given
        PatientModel model = getPersonModel(false);
        when(conceptService.getOrInit("concept")).thenReturn(getConcept());
        when(userService.getOrInit("user")).thenReturn(getUser());

        // When
        Patient result = mapper.modelToEntity(model);

        // Then
        assertResult(model, result);
    }

    @Test
    public void modelToEntityWithNullDependencies() {
        // Given
        PatientModel model = getPersonModel(true);
        when(conceptService.getOrInit("concept")).thenReturn(getConcept());
        when(userService.getOrInit("user")).thenReturn(getUser());

        // When
        Patient result = mapper.modelToEntity(model);

        // Then
        assertResult(model, result);
    }

    private void assertResult(Patient ety, PatientModel result) {
        assertEquals(ety.getGender(), result.getGender());
        assertEquals(ety.getBirthdate(), result.getBirthdate());
        assertEquals(ety.isBirthdateEstimated(), result.isBirthdateEstimated());
        assertEquals(ety.isDead(), result.isDead());
        assertEquals(ety.getDeathDate(), result.getDeathDate());
        assertEquals(ety.getCauseOfDeath().getUuid(), result.getCauseOfDeathUuid());
        assertEquals(ety.getCreator().getUuid(), result.getCreatorUuid());
        assertEquals(ety.getDateCreated(), result.getDateCreated());
        assertEquals(ety.getChangedBy().getUuid(), result.getChangedByUuid());
        assertEquals(ety.getDateChanged(), result.getDateChanged());
        assertEquals(ety.isVoided(), result.isVoided());
        assertEquals(ety.getVoidedBy().getUuid(), result.getVoidedByUuid());
        assertEquals(ety.getDateVoided(), result.getDateVoided());
        assertEquals(ety.getVoidReason(), result.getVoidReason());
        assertEquals(ety.getPatientCreator().getUuid(), result.getPatientCreatorUuid());
        assertEquals(ety.getPatientDateCreated(), result.getPatientDateCreated());
        assertEquals(ety.getPatientChangedBy().getUuid(), result.getPatientChangedByUuid());
        assertEquals(ety.getPatientDateChanged(), result.getPatientDateChanged());
        assertEquals(ety.isPatientVoided(), result.isPatientVoided());
        assertEquals(ety.getPatientVoidedBy().getUuid(), result.getPatientVoidedByUuid());
        assertEquals(ety.getPatientDateVoided(), result.getPatientDateVoided());
        assertEquals(ety.getPatientVoidReason(), result.getPatientVoidReason());
        assertEquals(ety.getUuid(), result.getUuid());
        assertEquals(ety.isDeathdateEstimated(), result.isDeathdateEstimated());
        assertEquals(ety.getBirthtime(), result.getBirthtime());
    }

    private Patient getPatientEty() {
        ConceptLight concept = new ConceptLight();
        concept.setUuid("causeOfDeath");

        UserLight user = new UserLight();
        user.setUuid("userId");

        Patient ety = new Patient();
        ety.setGender("M");
        ety.setBirthdate(LocalDate.of(1956, Month.OCTOBER, 22));
        ety.setBirthdateEstimated(true);
        ety.setDead(false);
        ety.setDeathDate(LocalDate.of(1988, Month.NOVEMBER, 1));
        ety.setCauseOfDeath(concept);
        ety.setCreator(user);
        ety.setDateCreated(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
        ety.setChangedBy(user);
        ety.setDateChanged(LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0));
        ety.setVoided(true);
        ety.setVoidedBy(user);
        ety.setDateVoided(LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0));
        ety.setVoidReason("voided");
        ety.setPatientCreator(user);
        ety.setPatientDateCreated(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
        ety.setPatientChangedBy(user);
        ety.setPatientDateChanged(LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0));
        ety.setPatientVoided(true);
        ety.setPatientVoidedBy(user);
        ety.setPatientDateVoided(LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0));
        ety.setPatientVoidReason("voided");
        ety.setUuid("patient");
        ety.setDeathdateEstimated(false);
        ety.setBirthtime(LocalTime.of(10, 10));
        return ety;
    }

    private void assertResult(final PatientModel model,
                              final Patient result) {
        assertEquals(model.getGender(), result.getGender());
        assertEquals(model.getBirthdate(), result.getBirthdate());
        assertEquals(model.isBirthdateEstimated(), result.isBirthdateEstimated());
        assertEquals(model.isDead(), result.isDead());
        assertEquals(model.getDeathDate(), result.getDeathDate());
        if (model.getCauseOfDeathUuid() != null) {
            assertEquals(model.getCauseOfDeathUuid(), result.getCauseOfDeath().getUuid());
        } else {
            assertNull(result.getCauseOfDeath());
        }
        if (model.getCreatorUuid() != null) {
            assertEquals(model.getCreatorUuid(), result.getCreator().getUuid());
        } else {
            assertNull(result.getCreator());
        }
        assertEquals(model.getDateCreated(), result.getDateCreated());
        if (model.getChangedByUuid() != null) {
            assertEquals(model.getChangedByUuid(), result.getChangedBy().getUuid());
        } else {
            assertNull(result.getChangedBy());
        }
        assertEquals(model.getDateChanged(), result.getDateChanged());
        assertEquals(model.isVoided(), result.isVoided());
        if (model.getVoidedByUuid() != null) {
            assertEquals(model.getVoidedByUuid(), result.getVoidedBy().getUuid());
        } else {
            assertNull(result.getVoidedBy());
        }
        assertEquals(model.getDateVoided(), result.getDateVoided());
        assertEquals(model.getVoidReason(), result.getVoidReason());
        if (model.getPatientCreatorUuid() != null) {
            assertEquals(model.getPatientCreatorUuid(), result.getPatientCreator().getUuid());
        } else {
            assertNull(result.getPatientCreator());
        }
        assertEquals(model.getPatientDateCreated(), result.getPatientDateCreated());
        if (model.getPatientChangedByUuid() != null) {
            assertEquals(model.getPatientChangedByUuid(), result.getPatientChangedBy().getUuid());
        } else {
            assertNull(result.getPatientChangedBy());
        }
        assertEquals(model.getPatientDateChanged(), result.getPatientDateChanged());
        assertEquals(model.isPatientVoided(), result.isPatientVoided());
        if (model.getPatientVoidedByUuid() != null) {
            assertEquals(model.getPatientVoidedByUuid(), result.getPatientVoidedBy().getUuid());
        } else {
            assertNull(result.getPatientVoidedBy());
        }
        assertEquals(model.getPatientDateVoided(), result.getPatientDateVoided());
        assertEquals(model.getPatientVoidReason(), result.getPatientVoidReason());
        assertEquals(model.getUuid(), result.getUuid());
        assertEquals(model.isDeathdateEstimated(), result.isDeathdateEstimated());
        assertEquals(model.getBirthtime(), result.getBirthtime());
    }

    private PatientModel getPersonModel(boolean nullDependencies) {
        PatientModel model = new PatientModel();
        model.setGender("M");
        model.setBirthdate(LocalDate.of(1956, Month.OCTOBER, 22));
        model.setBirthdateEstimated(false);
        model.setDead(false);
        model.setDeathDate(LocalDate.of(1988, Month.NOVEMBER, 1));
        model.setCauseOfDeathUuid(nullDependencies ? null : "concept");
        model.setCreatorUuid(nullDependencies ? null : "user");
        model.setDateCreated(nullDependencies ? null : LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
        model.setChangedByUuid(nullDependencies ? null : "user");
        model.setDateChanged(nullDependencies ? null : LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0));
        model.setVoided(true);
        model.setVoidedByUuid(nullDependencies ? null : "user");
        model.setDateVoided(nullDependencies ? null : LocalDateTime.of(1012, Month.JANUARY, 1, 0, 0));
        model.setVoidReason(nullDependencies ? null : "voided");
        model.setPatientCreatorUuid(nullDependencies ? null : "user");
        model.setPatientDateCreated(nullDependencies ? null : LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0));
        model.setPatientChangedByUuid(nullDependencies ? null : "user");
        model.setPatientDateChanged(nullDependencies ? null : LocalDateTime.of(2011, Month.JANUARY, 1, 0, 0));
        model.setPatientVoided(true);
        model.setPatientVoidedByUuid(nullDependencies ? null : "user");
        model.setPatientDateVoided(nullDependencies ? null : LocalDateTime.of(1012, Month.JANUARY, 1, 0, 0));
        model.setPatientVoidReason(nullDependencies ? null : "voided");
        model.setUuid("person");
        model.setDeathdateEstimated(false);
        model.setBirthtime(LocalTime.of(10, 10));
        return model;
    }

    private UserLight getUser() {
        UserLight user = new UserLight();
        user.setUuid("user");
        return user;
    }

    private ConceptLight getConcept() {
        ConceptLight concept = new ConceptLight();
        concept.setUuid("concept");
        return concept;
    }
}
