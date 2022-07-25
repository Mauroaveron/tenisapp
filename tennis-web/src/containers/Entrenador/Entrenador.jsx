import React, { useEffect, useState } from 'react';
import Typography from '../../components/Typography/Typography';
import { Button } from 'react-bootstrap';
import TableEntrenador from '../../components/Tables/TableEntrenador';
import EntrenadorModal from '../../components/Modals/EntrenadorModal';
import httpClient from '../../lib/httpClient';

let entrenadorInit = {
  nombre: '',
};

const Entrenador = (props) => {
  const [entrenadoresList, setEntrenadoresList] = useState([]);
  const [entrenadorData, setEntrenadorData] = useState(entrenadorInit);
  const [isEdit, setIsEdit] = useState(false);
  const [hasErrorInForm, setHasErrorInForm] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(async () => {
    await getEntrenadores();
  }, []);

  //Verbos
  const getEntrenadores = async () => {
    try {
      const data = await httpClient.get('/entrenadores');
      setEntrenadoresList(data);
    } catch (error) {
      console.log(error);
    }
  };

  const agregarEntrenador = async () => {
    try {
      const data = await httpClient.post(`/entrenadores`, { data: entrenadorData });
      setEntrenadoresList([...entrenadoresList, data]);
    } catch (error) {
      console.log(error);
    }
    handleCloseModal();
  };

  const editarEntrenador = async (id) => {
    try {
      const data = await httpClient.put(`/entrenadores/${id}`, { data: entrenadorData });
      setEntrenadoresList(entrenadoresList.map((item) => (item.id === id ? data : item)));
    } catch (error) {
      console.log(error);
    }
    handleCloseModal();
  };

  const borrarEntrenador = async (id) => {
    try {
      await httpClient.delete(`/entrenadores/${id}`);
      setEntrenadoresList(entrenadoresList.filter((entrenador) => entrenador.id !== id));
    } catch (error) {
      console.log(error);
    }
  };

  const calcularPartidosEnElDia = (entrenadorId) => {
    const partidos = [... partidosList];
    let partidosHoyEnEntrenador = 0;
    const hoy = new Date(Date.now()).setHours(0, 0, 0 ,0);
    partidos.map(partido => partido.fechaComienzo = new Date(partido.fechaComienzo).setHours(0, 0, 0, 0));
    partidos.forEach(element => {
      if(element.fechaComienzo === hoy && element.entrenador.id === entrenadorId){
        partidosHoyEnEntrenador++;
      }
    });
    return partidosHoyEnEntrenador;
  }
  
  // Buttons
  const handleEdit = (editData, event) => {
    event.preventDefault();
    handleOpenModal(true, editData);
  };

  const handleDelete = async (id, event) => {
    event.preventDefault();
    if (window.confirm('Estas seguro?')) {
      await borrarEntrenador(id);
    }
  };

  // Modal
  const handleOpenModal = (editarEntrenador = false, entrenadorToEdit = null) => {
    setIsEdit(editarEntrenador);

    if (editarEntrenador) {
      setEntrenadorData(entrenadorToEdit);
    }

    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setIsEdit(false);
    setHasErrorInForm(false);
    setEntrenadorData(entrenadorInit);
    setErrorMsg('');
  };

  // Form
  const handleChangeInputForm = (property, value) => {
    // Si el valor del input es vacío, entonces setea que hay un error
    value === '' ? setHasErrorInForm(true) : setHasErrorInForm(false);

    setEntrenadorData({ ...entrenadorData, [property]: value });
  };

  const handleSubmitForm = (e, form, isEdit) => {
    e.preventDefault();
    setHasErrorInForm(true);

    if (form.checkValidity()) isEdit ? editarEntrenador(entrenadorData.id) : agregarEntrenador();
  };

  // API

  return (
    <>
      <Typography id={'title-id'}>Entrenador</Typography>
      <div className="mb-2">
        <Button variant="success" onClick={() => handleOpenModal()}>
          Agregar entrenador
        </Button>
      </div>

      <TableEntrenador
        dataForTable={entrenadoresList}
        edit={handleEdit}
        delete={(id, event) => handleDelete(id, event)}
        calcularPartidosEnElDia={calcularPartidosEnElDia}
      />
      <EntrenadorModal
        show={openModal}
        onHide={handleCloseModal}
        isEdit={isEdit}
        handleChange={handleChangeInputForm}
        entrenador={entrenadorData}
        validated={hasErrorInForm}
        handleSubmit={handleSubmitForm}
        errorMsg={errorMsg}
      />
    </>
  );
};

export default Entrenador;