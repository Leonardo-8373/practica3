package com.example.rest.controller.dao;

import com.example.rest.controller.dao.implement.AdapterDao;
import com.example.rest.controller.exception.ListEmptyException;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList listAll;

    public ProyectoDao() {
        super(Proyecto.class);
    }

    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        this.persist(this.proyecto);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) {
        LinkedList list = getListAll();
        try {
            if (list.isEmpty()) {
                return false; // Si la lista está vacía, no hay nada que eliminar.
            }
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto p = (Proyecto) list.get(i);
                if (p.getId() == id) {
                    list.delete(i); // Eliminar el proyecto de la lista
                    this.listAll = list; // Actualizar la lista
                    return true; // Eliminación exitosa
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return false; // Si no se encontró el proyecto con el ID
    }

    
    


    // Método para obtener un proyecto por su ID
    public Proyecto get(int id) {
        LinkedList list = getListAll();
        try {
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto p = (Proyecto) list.get(i);
                if (p.getId() == id) {
                    return p;
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el proyecto
    }

    private Boolean verify(Proyecto a, Proyecto b, Integer type_order, String atributo) {
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            } else if (atributo.equalsIgnoreCase("inversion")) {
                return a.getInversion() > b.getInversion();
            } else if (atributo.equalsIgnoreCase("tiempoVida")) {
                return a.getTiempoVida() > b.getTiempoVida();
            }
        } else {
            if (atributo.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (atributo.equalsIgnoreCase("inversion")) {
                return a.getInversion() < b.getInversion();
            } else if (atributo.equalsIgnoreCase("tiempoVida")) {
                return a.getTiempoVida() < b.getTiempoVida();
            }
        }
        return false;
    }
}
