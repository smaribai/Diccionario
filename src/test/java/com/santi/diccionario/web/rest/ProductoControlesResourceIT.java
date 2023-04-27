package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Control;
import com.santi.diccionario.domain.Producto;
import com.santi.diccionario.domain.ProductoControles;
import com.santi.diccionario.repository.ProductoControlesRepository;
import com.santi.diccionario.service.ProductoControlesService;
import com.santi.diccionario.service.criteria.ProductoControlesCriteria;
import com.santi.diccionario.service.dto.ProductoControlesDTO;
import com.santi.diccionario.service.mapper.ProductoControlesMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductoControlesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductoControlesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/producto-controles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoControlesRepository productoControlesRepository;

    @Mock
    private ProductoControlesRepository productoControlesRepositoryMock;

    @Autowired
    private ProductoControlesMapper productoControlesMapper;

    @Mock
    private ProductoControlesService productoControlesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoControlesMockMvc;

    private ProductoControles productoControles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoControles createEntity(EntityManager em) {
        ProductoControles productoControles = new ProductoControles().descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoControles.setCodigoArancelario(producto);
        // Add required entity
        Control control;
        if (TestUtil.findAll(em, Control.class).isEmpty()) {
            control = ControlResourceIT.createEntity(em);
            em.persist(control);
            em.flush();
        } else {
            control = TestUtil.findAll(em, Control.class).get(0);
        }
        productoControles.setIdControl(control);
        return productoControles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoControles createUpdatedEntity(EntityManager em) {
        ProductoControles productoControles = new ProductoControles().descripcion(UPDATED_DESCRIPCION);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoControles.setCodigoArancelario(producto);
        // Add required entity
        Control control;
        if (TestUtil.findAll(em, Control.class).isEmpty()) {
            control = ControlResourceIT.createUpdatedEntity(em);
            em.persist(control);
            em.flush();
        } else {
            control = TestUtil.findAll(em, Control.class).get(0);
        }
        productoControles.setIdControl(control);
        return productoControles;
    }

    @BeforeEach
    public void initTest() {
        productoControles = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoControles() throws Exception {
        int databaseSizeBeforeCreate = productoControlesRepository.findAll().size();
        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);
        restProductoControlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createProductoControlesWithExistingId() throws Exception {
        // Create the ProductoControles with an existing ID
        productoControles.setId(1L);
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        int databaseSizeBeforeCreate = productoControlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoControlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoControles.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductoControlesWithEagerRelationshipsIsEnabled() throws Exception {
        when(productoControlesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoControlesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productoControlesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductoControlesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productoControlesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoControlesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productoControlesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get the productoControles
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL_ID, productoControles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoControles.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getProductoControlesByIdFiltering() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        Long id = productoControles.getId();

        defaultProductoControlesShouldBeFound("id.equals=" + id);
        defaultProductoControlesShouldNotBeFound("id.notEquals=" + id);

        defaultProductoControlesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductoControlesShouldNotBeFound("id.greaterThan=" + id);

        defaultProductoControlesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductoControlesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion equals to DEFAULT_DESCRIPCION
        defaultProductoControlesShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the productoControlesList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoControlesShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultProductoControlesShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the productoControlesList where descripcion not equals to UPDATED_DESCRIPCION
        defaultProductoControlesShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultProductoControlesShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the productoControlesList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoControlesShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion is not null
        defaultProductoControlesShouldBeFound("descripcion.specified=true");

        // Get all the productoControlesList where descripcion is null
        defaultProductoControlesShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion contains DEFAULT_DESCRIPCION
        defaultProductoControlesShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the productoControlesList where descripcion contains UPDATED_DESCRIPCION
        defaultProductoControlesShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductoControlesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultProductoControlesShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the productoControlesList where descripcion does not contain UPDATED_DESCRIPCION
        defaultProductoControlesShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductoControlesByCodigoArancelarioIsEqualToSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);
        Producto codigoArancelario;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            codigoArancelario = ProductoResourceIT.createEntity(em);
            em.persist(codigoArancelario);
            em.flush();
        } else {
            codigoArancelario = TestUtil.findAll(em, Producto.class).get(0);
        }
        em.persist(codigoArancelario);
        em.flush();
        productoControles.setCodigoArancelario(codigoArancelario);
        productoControlesRepository.saveAndFlush(productoControles);
        Long codigoArancelarioId = codigoArancelario.getId();

        // Get all the productoControlesList where codigoArancelario equals to codigoArancelarioId
        defaultProductoControlesShouldBeFound("codigoArancelarioId.equals=" + codigoArancelarioId);

        // Get all the productoControlesList where codigoArancelario equals to (codigoArancelarioId + 1)
        defaultProductoControlesShouldNotBeFound("codigoArancelarioId.equals=" + (codigoArancelarioId + 1));
    }

    @Test
    @Transactional
    void getAllProductoControlesByIdControlIsEqualToSomething() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);
        Control idControl;
        if (TestUtil.findAll(em, Control.class).isEmpty()) {
            idControl = ControlResourceIT.createEntity(em);
            em.persist(idControl);
            em.flush();
        } else {
            idControl = TestUtil.findAll(em, Control.class).get(0);
        }
        em.persist(idControl);
        em.flush();
        productoControles.setIdControl(idControl);
        productoControlesRepository.saveAndFlush(productoControles);
        Long idControlId = idControl.getId();

        // Get all the productoControlesList where idControl equals to idControlId
        defaultProductoControlesShouldBeFound("idControlId.equals=" + idControlId);

        // Get all the productoControlesList where idControl equals to (idControlId + 1)
        defaultProductoControlesShouldNotBeFound("idControlId.equals=" + (idControlId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductoControlesShouldBeFound(String filter) throws Exception {
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoControles.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductoControlesShouldNotBeFound(String filter) throws Exception {
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductoControles() throws Exception {
        // Get the productoControles
        restProductoControlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles
        ProductoControles updatedProductoControles = productoControlesRepository.findById(productoControles.getId()).get();
        // Disconnect from session so that the updates on updatedProductoControles are not directly saved in db
        em.detach(updatedProductoControles);
        updatedProductoControles.descripcion(UPDATED_DESCRIPCION);
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(updatedProductoControles);

        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoControlesWithPatch() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles using partial update
        ProductoControles partialUpdatedProductoControles = new ProductoControles();
        partialUpdatedProductoControles.setId(productoControles.getId());

        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoControles))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateProductoControlesWithPatch() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles using partial update
        ProductoControles partialUpdatedProductoControles = new ProductoControles();
        partialUpdatedProductoControles.setId(productoControles.getId());

        partialUpdatedProductoControles.descripcion(UPDATED_DESCRIPCION);

        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoControles))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeDelete = productoControlesRepository.findAll().size();

        // Delete the productoControles
        restProductoControlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoControles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
