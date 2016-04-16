<table><tr><td>
<h2>Objetivos</h2>
<ul><li>Controlar remotamente el robot multipropósito mediante una aplicación que se ejecute sobre la plataforma Android.<br>
</li><li>Realizar una aplicación de interfaz amigable que permita interactuar con el robot fluidamente.</li></ul>

<h2>Alcance</h2>
<ul><li>Los límites del proyecto se encuentran determinados por el desarrollo de la aplicación.<br>
</li><li>Este proyecto no incluye ninguna modificación al robot. El mismo ya cuenta con las características necesarias para el desarrollo de nuestra aplicación.<br>
</li><li>La lista de comandos queEste proyecto pertenece al proyecto final se implementarán se encuentra especificada en la siguiente sección.<br>
</td><td><img src='http://android-robot-control.googlecode.com/files/ARC_logo_small.jpg' /></td></tr></table>
<h2>Descripción del proyecto</h2>
El proyecto consta del desarrollo de una aplicación Android que permitirá ejecutar controles sobre el robot multipropósito.</li></ul>

Android es un sistema operativo de código libre para dispositivos móviles basado en el kernel de Linux. El mismo cuenta con un entorno de ejecución de aplicaciones basado en una máquina virtual de Java optimizada para dispositivos móviles, por lo que el lenguaje oficial de desarrollo de aplicaciones es Java.

Esta plataforma permite a las aplicaciones acceder a todos los recursos de los dispositivos mediante la invocación a librerias especificas para cada uno de ellos. Es posible de esta manera, obtener imagenes desde la camara del dispositivo, la posicion desde el GPS, conexiones WiFi o 3G, o captar movimientos desde el sensor del acelerómetro.

El robot multiproposito UADE es una computadora de arquitectura x86 que posee instalados un conjunto de servo-motores para la realización de determinados movimientos. Para esto, el robot posee instalado un kernel Linux version 2.4 modificado que cuenta con los drivers para los mencionados motores.

El robot posee dos motores tractores independientes que le permiten realizar los cuatro movimientos direccionales, avance, retroceso, giro a la derecha y giro a la izquierda. El robot también cuenta con un brazo mecánico. El mismo posee instalados tres servo-motores que permiten rotarlo, levantarlo o bajarlo, y apretar o liberar sus pinzas.

Para la comunicaciones, el robot cuenta con una placa WiFi norma B. La aplicación hará uso de esta conectividad para ejecutar los comandos remotamente sobre el robot.

El robot cuenta también con una camara de video del tipo webcam, y con un sistema de posicionamiento global. Ambas características serán utilizadas por la aplicación. La camara será utilizada para recibir imagenes en tiempo real con el fin de ser presentadas en la interfaz gráfica de la aplicación. Mientras que el GPS se utilizará para informar la posición del robot al usuario controlador.

En detalle, los controles a implementar en la aplicacion serán:
  * Movimiento hacia adelante
  * Movimiento hacia atrás.
  * Giros en ambas direcciones, izquierda y derecha.
  * Movimiento giratorio del brazo del robot.
  * Movimiento vertical del brazo del robot.
  * Movimiento de agarre de la pinza del robot.
  * Streaming de video desde la cámara del robot hacia la aplicación.
  * Informe de la localización del robot mediante el uso de su GPS.