import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/traduccion">
        <Translate contentKey="global.menu.entities.traduccion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/remitente">
        <Translate contentKey="global.menu.entities.remitente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/destinatario">
        <Translate contentKey="global.menu.entities.destinatario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/control">
        <Translate contentKey="global.menu.entities.control" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/provincia">
        <Translate contentKey="global.menu.entities.provincia" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/divisa">
        <Translate contentKey="global.menu.entities.divisa" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/clasificaciones">
        <Translate contentKey="global.menu.entities.clasificaciones" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/clasificar">
        <Translate contentKey="global.menu.entities.clasificar" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/idioma">
        <Translate contentKey="global.menu.entities.idioma" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pais">
        <Translate contentKey="global.menu.entities.pais" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto">
        <Translate contentKey="global.menu.entities.producto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-controles">
        <Translate contentKey="global.menu.entities.productoControles" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-cliente">
        <Translate contentKey="global.menu.entities.tipoCliente" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
