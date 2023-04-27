import traduccion from 'app/entities/traduccion/traduccion.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import remitente from 'app/entities/remitente/remitente.reducer';
import destinatario from 'app/entities/destinatario/destinatario.reducer';
import control from 'app/entities/control/control.reducer';
import provincia from 'app/entities/provincia/provincia.reducer';
import divisa from 'app/entities/divisa/divisa.reducer';
import clasificaciones from 'app/entities/clasificaciones/clasificaciones.reducer';
import clasificar from 'app/entities/clasificar/clasificar.reducer';
import idioma from 'app/entities/idioma/idioma.reducer';
import pais from 'app/entities/pais/pais.reducer';
import producto from 'app/entities/producto/producto.reducer';
import productoControles from 'app/entities/producto-controles/producto-controles.reducer';
import tipoCliente from 'app/entities/tipo-cliente/tipo-cliente.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  traduccion,
  cliente,
  remitente,
  destinatario,
  control,
  provincia,
  divisa,
  clasificaciones,
  clasificar,
  idioma,
  pais,
  producto,
  productoControles,
  tipoCliente,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
