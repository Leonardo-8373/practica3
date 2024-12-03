from flask import Flask, render_template, request, redirect, url_for
import datetime
import json

app = Flask(__name__)

# Archivos para almacenar proyectos y transacciones
PROYECTOS_FILE = '/home/leonardo/Escritorio/ProyectoEnergia/java_backend/media/Proyectos.json'
TRANSACCIONES_FILE = '/home/leonardo/Escritorio/ProyectoEnergia/java_backend/media/Transacciones.json'

# Función para cargar proyectos desde el archivo JSON
def cargar_proyectos():
    try:
        with open(PROYECTOS_FILE, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

# Función para guardar proyectos en el archivo JSON
def guardar_proyectos(proyectos):
    with open(PROYECTOS_FILE, 'w') as f:
        json.dump(proyectos, f, indent=4)

# Función para cargar transacciones desde el archivo JSON
def cargar_transacciones():
    try:
        with open(TRANSACCIONES_FILE, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

# Función para guardar transacciones en el archivo JSON
def guardar_transacciones(transacciones):
    with open(TRANSACCIONES_FILE, 'w') as f:
        json.dump(transacciones, f, indent=4)

# Ruta para la página de inicio
@app.route('/')
def index():
    return redirect(url_for('mostrar_proyectos'))

# Ruta para mostrar todos los proyectos
@app.route('/mostrar_proyectos')
def mostrar_proyectos():
    proyectos = cargar_proyectos()
    return render_template('mostrar_proyectos.html', proyectos=proyectos)

# Ruta para crear o actualizar un proyecto
@app.route('/crear_proyecto', methods=['GET', 'POST'])
def crear_proyecto():
    if request.method == 'POST':
        proyecto = {
            'id': len(cargar_proyectos()) + 1,
            'nombre': request.form['nombre'],
            'descripcion': request.form['descripcion'],
            'inversion': float(request.form['inversion']),
            'tiempoVida': int(request.form['tiempoVida']),
            'fechaInicio': request.form['fechaInicio'],
            'fechaFin': request.form['fechaFin'],
            'inversionistas': request.form['inversionistas'].split(','),
            'electricidadGeneradaDiaria': float(request.form['electricidadGeneradaDiaria'])
        }
        proyectos = cargar_proyectos()
        proyectos.append(proyecto)
        guardar_proyectos(proyectos)

        # Registrar la transacción
        transaccion = {
            'id': len(cargar_transacciones()) + 1,
            'tipoOperacion': 'Crear Proyecto',
            'nombreProyecto': proyecto['nombre'],
            'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
            'descripcion': f'Se creó el proyecto {proyecto["nombre"]}.'
        }
        transacciones = cargar_transacciones()
        transacciones.append(transaccion)
        guardar_transacciones(transacciones)

        return redirect(url_for('mostrar_proyectos'))

    return render_template('proyecto_form.html', title='Crear Proyecto', proyecto={})

# Ruta para mostrar las transacciones
@app.route('/mostrar_transacciones')
def mostrar_transacciones():
    transacciones = cargar_transacciones()
    return render_template('mostrar_transacciones.html', transacciones=transacciones)

# Ruta para crear una transacción
@app.route('/crear_transaccion', methods=['GET', 'POST'])
def crear_transaccion():
    if request.method == 'POST':
        transaccion = {
            'id': len(cargar_transacciones()) + 1,
            'tipoOperacion': request.form['tipoOperacion'],
            'nombreProyecto': request.form['nombreProyecto'],
            'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
            'descripcion': request.form['descripcion']
        }
        transacciones = cargar_transacciones()
        transacciones.append(transaccion)
        guardar_transacciones(transacciones)
        return redirect(url_for('mostrar_transacciones'))

    proyectos = cargar_proyectos()  # Para mostrar proyectos en el formulario de transacción
    return render_template('transaccion_form.html', title='Crear Transacción', proyectos=proyectos)

# Ruta para eliminar un proyecto
@app.route('/eliminar_proyecto/<int:id>', methods=['GET'])
def eliminar_proyecto(id):
    proyectos = cargar_proyectos()
    proyecto = next((p for p in proyectos if p['id'] == id), None)
    proyectos = [p for p in proyectos if p['id'] != id]
    guardar_proyectos(proyectos)

    # Registrar la transacción
    transaccion = {
        'id': len(cargar_transacciones()) + 1,
        'tipoOperacion': 'Eliminar Proyecto',
        'nombreProyecto': proyecto['nombre'] if proyecto else f'ID {id}',
        'fecha': datetime.datetime.now().strftime('%Y-%m-%d'),
        'descripcion': f'Se eliminó el proyecto con ID {id}.'
    }
    transacciones = cargar_transacciones()
    transacciones.append(transaccion)
    guardar_transacciones(transacciones)

    return redirect(url_for('mostrar_proyectos'))

# Ruta para eliminar una transacción
@app.route('/eliminar_transaccion/<int:id>', methods=['GET'])
def eliminar_transaccion(id):
    transacciones = cargar_transacciones()
    transacciones = [t for t in transacciones if t['id'] != id]
    guardar_transacciones(transacciones)
    return redirect(url_for('mostrar_transacciones'))

if __name__ == '__main__':
    app.run(debug=True)
