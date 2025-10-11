# ServoMotor

Este proyecto está creado para controlar un servo motor de marca Phidget utilizando su placa de control. Está escrito en Java utilizando el entorno de programación de Eclipse. Este programa es parte de un trabajo de tesis. 

## Requisitos

- Java 8 o superior
- Placa de control Phidget
- Servo motor Phidget
- Entorno de programación de Eclipse
- Librería Phidget Java (descargable desde [Phidgets Java SDK](https://www.phidgets.com/docs/Language_-_Java))

## Instalación

1. Clona este repositorio.
2. Importa el proyecto a Eclipse.
3. Descarga e instala la librería Phidget Java al proyecto de Ecliipse.
4. Conecta el servo motor y la placa de control Phidget al computador.

## Uso

El programa permite controlar el estado del servo motor según el estado que se encuentra en la base de datos. Cuando el estado del servo motor está en activo, el servo motor empieza a mover, si está desactivado, deja de mover.