package com.santi.diccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.diccionario.IntegrationTest;
import com.santi.diccionario.domain.Producto;
import com.santi.diccionario.domain.Producto;
import com.santi.diccionario.repository.ProductoRepository;
import com.santi.diccionario.service.ProductoService;
import com.santi.diccionario.service.criteria.ProductoCriteria;
import com.santi.diccionario.service.dto.ProductoDTO;
import com.santi.diccionario.service.mapper.ProductoMapper;
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
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductoResourceIT {

    private static final String DEFAULT_CODIGO_ARANCELARIO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ARANCELARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;
    private static final Integer SMALLER_NIVEL = 1 - 1;

    private static final String DEFAULT_C_N_CODE = "AAAAAAAAAA";
    private static final String UPDATED_C_N_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LONGITUD_CN_CODE = 1;
    private static final Integer UPDATED_LONGITUD_CN_CODE = 2;
    private static final Integer SMALLER_LONGITUD_CN_CODE = 1 - 1;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoRepository productoRepository;

    @Mock
    private ProductoRepository productoRepositoryMock;

    @Autowired
    private ProductoMapper productoMapper;

    @Mock
    private ProductoService productoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMockMvc;

    private Producto producto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .codigoArancelario(DEFAULT_CODIGO_ARANCELARIO)
            .nivel(DEFAULT_NIVEL)
            .cNCode(DEFAULT_C_N_CODE)
            .longitudCNCode(DEFAULT_LONGITUD_CN_CODE)
            .descripcion(DEFAULT_DESCRIPCION);
        return producto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .codigoArancelario(UPDATED_CODIGO_ARANCELARIO)
            .nivel(UPDATED_NIVEL)
            .cNCode(UPDATED_C_N_CODE)
            .longitudCNCode(UPDATED_LONGITUD_CN_CODE)
            .descripcion(UPDATED_DESCRIPCION);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();
        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigoArancelario()).isEqualTo(DEFAULT_CODIGO_ARANCELARIO);
        assertThat(testProducto.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testProducto.getcNCode()).isEqualTo(DEFAULT_C_N_CODE);
        assertThat(testProducto.getLongitudCNCode()).isEqualTo(DEFAULT_LONGITUD_CN_CODE);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createProductoWithExistingId() throws Exception {
        // Create the Producto with an existing ID
        producto.setId(1L);
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoArancelarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setCodigoArancelario(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setDescripcion(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoArancelario").value(hasItem(DEFAULT_CODIGO_ARANCELARIO)))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)))
            .andExpect(jsonPath("$.[*].cNCode").value(hasItem(DEFAULT_C_N_CODE)))
            .andExpect(jsonPath("$.[*].longitudCNCode").value(hasItem(DEFAULT_LONGITUD_CN_CODE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductosWithEagerRelationshipsIsEnabled() throws Exception {
        when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.codigoArancelario").value(DEFAULT_CODIGO_ARANCELARIO))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL))
            .andExpect(jsonPath("$.cNCode").value(DEFAULT_C_N_CODE))
            .andExpect(jsonPath("$.longitudCNCode").value(DEFAULT_LONGITUD_CN_CODE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getProductosByIdFiltering() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        Long id = producto.getId();

        defaultProductoShouldBeFound("id.equals=" + id);
        defaultProductoShouldNotBeFound("id.notEquals=" + id);

        defaultProductoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.greaterThan=" + id);

        defaultProductoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario equals to DEFAULT_CODIGO_ARANCELARIO
        defaultProductoShouldBeFound("codigoArancelario.equals=" + DEFAULT_CODIGO_ARANCELARIO);

        // Get all the productoList where codigoArancelario equals to UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldNotBeFound("codigoArancelario.equals=" + UPDATED_CODIGO_ARANCELARIO);
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario not equals to DEFAULT_CODIGO_ARANCELARIO
        defaultProductoShouldNotBeFound("codigoArancelario.notEquals=" + DEFAULT_CODIGO_ARANCELARIO);

        // Get all the productoList where codigoArancelario not equals to UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldBeFound("codigoArancelario.notEquals=" + UPDATED_CODIGO_ARANCELARIO);
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario in DEFAULT_CODIGO_ARANCELARIO or UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldBeFound("codigoArancelario.in=" + DEFAULT_CODIGO_ARANCELARIO + "," + UPDATED_CODIGO_ARANCELARIO);

        // Get all the productoList where codigoArancelario equals to UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldNotBeFound("codigoArancelario.in=" + UPDATED_CODIGO_ARANCELARIO);
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario is not null
        defaultProductoShouldBeFound("codigoArancelario.specified=true");

        // Get all the productoList where codigoArancelario is null
        defaultProductoShouldNotBeFound("codigoArancelario.specified=false");
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario contains DEFAULT_CODIGO_ARANCELARIO
        defaultProductoShouldBeFound("codigoArancelario.contains=" + DEFAULT_CODIGO_ARANCELARIO);

        // Get all the productoList where codigoArancelario contains UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldNotBeFound("codigoArancelario.contains=" + UPDATED_CODIGO_ARANCELARIO);
    }

    @Test
    @Transactional
    void getAllProductosByCodigoArancelarioNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where codigoArancelario does not contain DEFAULT_CODIGO_ARANCELARIO
        defaultProductoShouldNotBeFound("codigoArancelario.doesNotContain=" + DEFAULT_CODIGO_ARANCELARIO);

        // Get all the productoList where codigoArancelario does not contain UPDATED_CODIGO_ARANCELARIO
        defaultProductoShouldBeFound("codigoArancelario.doesNotContain=" + UPDATED_CODIGO_ARANCELARIO);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel equals to DEFAULT_NIVEL
        defaultProductoShouldBeFound("nivel.equals=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel equals to UPDATED_NIVEL
        defaultProductoShouldNotBeFound("nivel.equals=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel not equals to DEFAULT_NIVEL
        defaultProductoShouldNotBeFound("nivel.notEquals=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel not equals to UPDATED_NIVEL
        defaultProductoShouldBeFound("nivel.notEquals=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel in DEFAULT_NIVEL or UPDATED_NIVEL
        defaultProductoShouldBeFound("nivel.in=" + DEFAULT_NIVEL + "," + UPDATED_NIVEL);

        // Get all the productoList where nivel equals to UPDATED_NIVEL
        defaultProductoShouldNotBeFound("nivel.in=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel is not null
        defaultProductoShouldBeFound("nivel.specified=true");

        // Get all the productoList where nivel is null
        defaultProductoShouldNotBeFound("nivel.specified=false");
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel is greater than or equal to DEFAULT_NIVEL
        defaultProductoShouldBeFound("nivel.greaterThanOrEqual=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel is greater than or equal to UPDATED_NIVEL
        defaultProductoShouldNotBeFound("nivel.greaterThanOrEqual=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel is less than or equal to DEFAULT_NIVEL
        defaultProductoShouldBeFound("nivel.lessThanOrEqual=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel is less than or equal to SMALLER_NIVEL
        defaultProductoShouldNotBeFound("nivel.lessThanOrEqual=" + SMALLER_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel is less than DEFAULT_NIVEL
        defaultProductoShouldNotBeFound("nivel.lessThan=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel is less than UPDATED_NIVEL
        defaultProductoShouldBeFound("nivel.lessThan=" + UPDATED_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosByNivelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where nivel is greater than DEFAULT_NIVEL
        defaultProductoShouldNotBeFound("nivel.greaterThan=" + DEFAULT_NIVEL);

        // Get all the productoList where nivel is greater than SMALLER_NIVEL
        defaultProductoShouldBeFound("nivel.greaterThan=" + SMALLER_NIVEL);
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode equals to DEFAULT_C_N_CODE
        defaultProductoShouldBeFound("cNCode.equals=" + DEFAULT_C_N_CODE);

        // Get all the productoList where cNCode equals to UPDATED_C_N_CODE
        defaultProductoShouldNotBeFound("cNCode.equals=" + UPDATED_C_N_CODE);
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode not equals to DEFAULT_C_N_CODE
        defaultProductoShouldNotBeFound("cNCode.notEquals=" + DEFAULT_C_N_CODE);

        // Get all the productoList where cNCode not equals to UPDATED_C_N_CODE
        defaultProductoShouldBeFound("cNCode.notEquals=" + UPDATED_C_N_CODE);
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode in DEFAULT_C_N_CODE or UPDATED_C_N_CODE
        defaultProductoShouldBeFound("cNCode.in=" + DEFAULT_C_N_CODE + "," + UPDATED_C_N_CODE);

        // Get all the productoList where cNCode equals to UPDATED_C_N_CODE
        defaultProductoShouldNotBeFound("cNCode.in=" + UPDATED_C_N_CODE);
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode is not null
        defaultProductoShouldBeFound("cNCode.specified=true");

        // Get all the productoList where cNCode is null
        defaultProductoShouldNotBeFound("cNCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode contains DEFAULT_C_N_CODE
        defaultProductoShouldBeFound("cNCode.contains=" + DEFAULT_C_N_CODE);

        // Get all the productoList where cNCode contains UPDATED_C_N_CODE
        defaultProductoShouldNotBeFound("cNCode.contains=" + UPDATED_C_N_CODE);
    }

    @Test
    @Transactional
    void getAllProductosBycNCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where cNCode does not contain DEFAULT_C_N_CODE
        defaultProductoShouldNotBeFound("cNCode.doesNotContain=" + DEFAULT_C_N_CODE);

        // Get all the productoList where cNCode does not contain UPDATED_C_N_CODE
        defaultProductoShouldBeFound("cNCode.doesNotContain=" + UPDATED_C_N_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode equals to DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.equals=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode equals to UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.equals=" + UPDATED_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode not equals to DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.notEquals=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode not equals to UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.notEquals=" + UPDATED_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode in DEFAULT_LONGITUD_CN_CODE or UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.in=" + DEFAULT_LONGITUD_CN_CODE + "," + UPDATED_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode equals to UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.in=" + UPDATED_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode is not null
        defaultProductoShouldBeFound("longitudCNCode.specified=true");

        // Get all the productoList where longitudCNCode is null
        defaultProductoShouldNotBeFound("longitudCNCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode is greater than or equal to DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.greaterThanOrEqual=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode is greater than or equal to UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.greaterThanOrEqual=" + UPDATED_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode is less than or equal to DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.lessThanOrEqual=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode is less than or equal to SMALLER_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.lessThanOrEqual=" + SMALLER_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode is less than DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.lessThan=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode is less than UPDATED_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.lessThan=" + UPDATED_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByLongitudCNCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where longitudCNCode is greater than DEFAULT_LONGITUD_CN_CODE
        defaultProductoShouldNotBeFound("longitudCNCode.greaterThan=" + DEFAULT_LONGITUD_CN_CODE);

        // Get all the productoList where longitudCNCode is greater than SMALLER_LONGITUD_CN_CODE
        defaultProductoShouldBeFound("longitudCNCode.greaterThan=" + SMALLER_LONGITUD_CN_CODE);
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion not equals to UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the productoList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion is not null
        defaultProductoShouldBeFound("descripcion.specified=true");

        // Get all the productoList where descripcion is null
        defaultProductoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion contains DEFAULT_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion contains UPDATED_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultProductoShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the productoList where descripcion does not contain UPDATED_DESCRIPCION
        defaultProductoShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllProductosByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);
        Producto parent;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            parent = ProductoResourceIT.createEntity(em);
            em.persist(parent);
            em.flush();
        } else {
            parent = TestUtil.findAll(em, Producto.class).get(0);
        }
        em.persist(parent);
        em.flush();
        producto.setParent(parent);
        productoRepository.saveAndFlush(producto);
        Long parentId = parent.getId();

        // Get all the productoList where parent equals to parentId
        defaultProductoShouldBeFound("parentId.equals=" + parentId);

        // Get all the productoList where parent equals to (parentId + 1)
        defaultProductoShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductoShouldBeFound(String filter) throws Exception {
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoArancelario").value(hasItem(DEFAULT_CODIGO_ARANCELARIO)))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)))
            .andExpect(jsonPath("$.[*].cNCode").value(hasItem(DEFAULT_C_N_CODE)))
            .andExpect(jsonPath("$.[*].longitudCNCode").value(hasItem(DEFAULT_LONGITUD_CN_CODE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductoShouldNotBeFound(String filter) throws Exception {
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .codigoArancelario(UPDATED_CODIGO_ARANCELARIO)
            .nivel(UPDATED_NIVEL)
            .cNCode(UPDATED_C_N_CODE)
            .longitudCNCode(UPDATED_LONGITUD_CN_CODE)
            .descripcion(UPDATED_DESCRIPCION);
        ProductoDTO productoDTO = productoMapper.toDto(updatedProducto);

        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigoArancelario()).isEqualTo(UPDATED_CODIGO_ARANCELARIO);
        assertThat(testProducto.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testProducto.getcNCode()).isEqualTo(UPDATED_C_N_CODE);
        assertThat(testProducto.getLongitudCNCode()).isEqualTo(UPDATED_LONGITUD_CN_CODE);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto.codigoArancelario(UPDATED_CODIGO_ARANCELARIO).nivel(UPDATED_NIVEL).descripcion(UPDATED_DESCRIPCION);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigoArancelario()).isEqualTo(UPDATED_CODIGO_ARANCELARIO);
        assertThat(testProducto.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testProducto.getcNCode()).isEqualTo(DEFAULT_C_N_CODE);
        assertThat(testProducto.getLongitudCNCode()).isEqualTo(DEFAULT_LONGITUD_CN_CODE);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto
            .codigoArancelario(UPDATED_CODIGO_ARANCELARIO)
            .nivel(UPDATED_NIVEL)
            .cNCode(UPDATED_C_N_CODE)
            .longitudCNCode(UPDATED_LONGITUD_CN_CODE)
            .descripcion(UPDATED_DESCRIPCION);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigoArancelario()).isEqualTo(UPDATED_CODIGO_ARANCELARIO);
        assertThat(testProducto.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testProducto.getcNCode()).isEqualTo(UPDATED_C_N_CODE);
        assertThat(testProducto.getLongitudCNCode()).isEqualTo(UPDATED_LONGITUD_CN_CODE);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, producto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
