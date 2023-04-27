package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Idioma;
import com.santi.diccionario.repository.IdiomaRepository;
import com.santi.diccionario.service.criteria.IdiomaCriteria;
import com.santi.diccionario.service.dto.IdiomaDTO;
import com.santi.diccionario.service.mapper.IdiomaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IdiomaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdiomaResourceIT {

    private static final String DEFAULT_CODIGO = "AA";
    private static final String UPDATED_CODIGO = "BB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/idiomas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private IdiomaMapper idiomaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdiomaMockMvc;

    private Idioma idioma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idioma createEntity(EntityManager em) {
        Idioma idioma = new Idioma().codigo(DEFAULT_CODIGO).nombre(DEFAULT_NOMBRE);
        return idioma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idioma createUpdatedEntity(EntityManager em) {
        Idioma idioma = new Idioma().codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
        return idioma;
    }

    @BeforeEach
    public void initTest() {
        idioma = createEntity(em);
    }

    @Test
    @Transactional
    void createIdioma() throws Exception {
        int databaseSizeBeforeCreate = idiomaRepository.findAll().size();
        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);
        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isCreated());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeCreate + 1);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testIdioma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createIdiomaWithExistingId() throws Exception {
        // Create the Idioma with an existing ID
        idioma.setId(1L);
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        int databaseSizeBeforeCreate = idiomaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = idiomaRepository.findAll().size();
        // set the field null
        idioma.setCodigo(null);

        // Create the Idioma, which fails.
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isBadRequest());

        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = idiomaRepository.findAll().size();
        // set the field null
        idioma.setNombre(null);

        // Create the Idioma, which fails.
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isBadRequest());

        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIdiomas() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idioma.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getIdioma() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get the idioma
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL_ID, idioma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idioma.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getIdiomasByIdFiltering() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        Long id = idioma.getId();

        defaultIdiomaShouldBeFound("id.equals=" + id);
        defaultIdiomaShouldNotBeFound("id.notEquals=" + id);

        defaultIdiomaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIdiomaShouldNotBeFound("id.greaterThan=" + id);

        defaultIdiomaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIdiomaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo equals to DEFAULT_CODIGO
        defaultIdiomaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the idiomaList where codigo equals to UPDATED_CODIGO
        defaultIdiomaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo not equals to DEFAULT_CODIGO
        defaultIdiomaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the idiomaList where codigo not equals to UPDATED_CODIGO
        defaultIdiomaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultIdiomaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the idiomaList where codigo equals to UPDATED_CODIGO
        defaultIdiomaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo is not null
        defaultIdiomaShouldBeFound("codigo.specified=true");

        // Get all the idiomaList where codigo is null
        defaultIdiomaShouldNotBeFound("codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo contains DEFAULT_CODIGO
        defaultIdiomaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the idiomaList where codigo contains UPDATED_CODIGO
        defaultIdiomaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllIdiomasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where codigo does not contain DEFAULT_CODIGO
        defaultIdiomaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the idiomaList where codigo does not contain UPDATED_CODIGO
        defaultIdiomaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre equals to DEFAULT_NOMBRE
        defaultIdiomaShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the idiomaList where nombre equals to UPDATED_NOMBRE
        defaultIdiomaShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre not equals to DEFAULT_NOMBRE
        defaultIdiomaShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the idiomaList where nombre not equals to UPDATED_NOMBRE
        defaultIdiomaShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultIdiomaShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the idiomaList where nombre equals to UPDATED_NOMBRE
        defaultIdiomaShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre is not null
        defaultIdiomaShouldBeFound("nombre.specified=true");

        // Get all the idiomaList where nombre is null
        defaultIdiomaShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreContainsSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre contains DEFAULT_NOMBRE
        defaultIdiomaShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the idiomaList where nombre contains UPDATED_NOMBRE
        defaultIdiomaShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllIdiomasByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList where nombre does not contain DEFAULT_NOMBRE
        defaultIdiomaShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the idiomaList where nombre does not contain UPDATED_NOMBRE
        defaultIdiomaShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIdiomaShouldBeFound(String filter) throws Exception {
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idioma.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIdiomaShouldNotBeFound(String filter) throws Exception {
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIdioma() throws Exception {
        // Get the idioma
        restIdiomaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdioma() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma
        Idioma updatedIdioma = idiomaRepository.findById(idioma.getId()).get();
        // Disconnect from session so that the updates on updatedIdioma are not directly saved in db
        em.detach(updatedIdioma);
        updatedIdioma.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(updatedIdioma);

        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idiomaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testIdioma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idiomaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdiomaWithPatch() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma using partial update
        Idioma partialUpdatedIdioma = new Idioma();
        partialUpdatedIdioma.setId(idioma.getId());

        partialUpdatedIdioma.codigo(UPDATED_CODIGO);

        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdioma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdioma))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testIdioma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateIdiomaWithPatch() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma using partial update
        Idioma partialUpdatedIdioma = new Idioma();
        partialUpdatedIdioma.setId(idioma.getId());

        partialUpdatedIdioma.codigo(UPDATED_CODIGO).nombre(UPDATED_NOMBRE);

        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdioma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdioma))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testIdioma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, idiomaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setId(count.incrementAndGet());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdioma() throws Exception {
        // Initialize the database
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeDelete = idiomaRepository.findAll().size();

        // Delete the idioma
        restIdiomaMockMvc
            .perform(delete(ENTITY_API_URL_ID, idioma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
