# Introduccion #

Esta guia explicara como configurar el entorno de desarrollo para trabajar con este proyecto. Desde la obtencion del software a utilizar, hasta descargar el codigo del repositorio.
Asi mismo, la especificacion del software a utilizar servira como configuracion "oficial" de desarrollo para el proyecto.

# Desarrollo #

## 1) Software a utilizar. ##
  * Java JDK 1.6.0\_13
  * Eclipse Ganymade 3.4.2
  * Android SDK 1.5
  * ADT Eclipse Plugin 0.9.1
  * Subversive Eclipse Plugin
  * Polarion SVN conectors Eclipse Plugin.

## 2) Descargar el software necesario. ##
  * Descargar el **JDK** desde https://cds.sun.com/is-bin/INTERSHOP.enfinity/WFS/CDS-CDS_Developer-Site/en_US/-/USD/ViewProductDetail-Start?ProductRef=jdk-6u13-oth-JPR@CDS-CDS_Developer
  * Descargar **Eclipse IDE for Java Developers** desde http://www.eclipse.org/downloads/
  * Descargar el **Android SDK 1.5** desde http://developer.android.com/sdk/1.5_r1/index.html

## 3) Descomprimir archivos descargados. ##
Una vez ubicados en el directorio de descargas, descomprimimos los archivos descargados con los siguientes comandos:
```
unzip <path_descargas>/android-sdk-linux_x86-1.5_r1.zip
tar zxvf <path_descargas>/eclipse-java-ganymede-SR2-linux-gtk.tar.gz
```

## 4) Setear variables de entorno. ##
  * En una terminal ( o consola ), configuramos las variables de entorno necesarias.
```
export JAVA_HOME=./jdk1.6.0_13/
export JRE_HOME=./jdk1.6.0_13/jre/
export ANDROID_HOME=./android-sdk-linux_x86-1.5_r1
```
  * Agregar las lineas anteriores al final del archivo de configuracion _.bashrc_ para que la configuracion de las variables de entorno este siempre disponible para el usuario. Ejecutar los siguientes comando:
```
echo "export JAVA_HOME=./jdk1.6.0_13/" >> $HOME/.bashrc
echo "export JRE_HOME=./jdk1.6.0_13/jre/" >> $HOME/.bashrc
echo "export ANDROID_HOME=./android-sdk-linux_x86-1.5_r1/" >> $HOME/.bashrc
```

## 5) Configurar Workspace. ##
  * Ejecutar el Eclipse. Una vez iniciado el programa, nos solicitara configurar el **_workspace_** donde seran guardados, por defecto, los proyectos en los que trabajemos. El workspace puede ser cualquier directorio sobre el cual el usuario debera poseer todos los permisos, lectura, escritura y ejecucion.

## 6) Instalar plugins del Eclipse. ##
  * El eclipse cuenta con una herramienta para instalar y actualizar plugins directamente desde adentro del entorno. Para acceder a esta herramieta debemos ingresar al menu de **_Help_** > **_Software Updates..._**. Una vez que estamos en la nueva ventana, cambiar a la solapa **_Available Software_**.

### 6.1) Plugin de Android. ###
  * Clickear en el boton **_Add Site..._** y agregar la siguiente URL en el dialogo:
```
http://dl-ssl.google.com/android/eclipse/
```
  * Una vez agregada la url a la lista, chequearla. Seleccionar todos los sub-items de la lista, y presionar el boton de **_Install..._**
  * El Eclipse descargara los plugins y solicitara la aprobacion de sus terminos de uso. Una vez aceptado esto, la instalacion continuara. Una vez finalizada la instalacion, se recomienda reiniciar el Eclipse.
  * Una vez reiniciado el Eclipse, ir al menu **_Window_** > **_Preferences_**. En la lista de la izquierda, seleccionar la opcion **_Android_**, donde deberemos configurar el directorio donde se encuentra el SDK de Android.

### 6.2) Plugin de Subversive. ###
  * Clickear en el boton **_Add Site..._** y agregar la siguiente URL en el dialogo:
```
http://download.eclipse.org/technology/subversive/0.7/update-site/
```
  * Una vez agregada la url a la lista, chequearla. De los sub-items desplegados, el unico necesario es **_Subversive SVN Team Provider Plugin_**. Chequearlo y presionar el boton de **_Install.._**.
  * El Eclipse descargara los plugins y solicitara la aprobacion de sus terminos de uso. Una vez aceptado esto, la instalacion continuara. Una vez finalizada la instalacion, se recomienda reiniciar el Eclipse.

### 6.3) Plugin de Polarion SVN conectors. ###
  * Clickear en el boton **_Add Site..._** y agregar la siguiente URL en el dialogo:
```
http://www.polarion.org/projects/subversive/download/eclipse/2.0/ganymede-site/
```
  * Una vez agregada la url a la lista, chequearla. De los sub-items desplegados, el unico necesario es **_Subversive SVN Connectors_**. Chequearlo y presionar el boton de   **_Install.._**.
  * El Eclipse descargara los plugins y solicitara la aprobacion de sus terminos de uso. Una vez aceptado esto, la instalacion continuara. Una vez finalizada la instalacion, se recomienda reiniciar el Eclipse.

## 7) Formateo de Codigo. ##
Para unificar la manera en la que visualizamos el codigo, utilizaremos la herramienta de formateo automatico de codigo del Eclipse, con una configuracion personalizada.
  * Descargar el archivo de configuracion de formateo de codigo de la seccion de downloads del proyecto o desde la siguiente direccion:
```
http://android-robot-control.googlecode.com/files/eclipse-code-formatter.xml
```
  * Dentro del Eclipse, ir a **_Window_** > **_Preferences_**.
  * En la nueva ventana, navegar por el menu de la izquierda hasta **_Java_** > **_Code Style_** > **_Formatter_** >
  * Hacer click en el boton de **_Import_**, y seleccionar el archivo anteriormente descargado.
  * Chequear que se haya seleccionado la nueva configuracion en el Combo o DropDown identificado como **_Active Profile_**.
  * Aceptar la configuracion haciendo click en el boton **_Ok_**
  * Para formatear el codigo presionar **_Ctrl+Shift+F_**

## 8) Descargar el codigo del repositorio. ##
  * Dentro del Eclipse, ir a **_Window_** > **_Open Perspective_** > **_Other_**.
  * En la nueva ventana, seleccionar la vista llamada **_SVN Reposity Exploring_**.
  * En la vista **_SVN Repositories_** ( a la izquierda del Eclipse ), hacer click en el icono del cilindro amarillo con el signo + de color verde.
  * Utilizaremos unicamente la configuracion ubicada en la solapa **_General_** ya que son suficientes.
  * Vamos a sincronizar el Eclipse con la raiz del SVN para poder descargar tanto del Trunk, de las Branches como de la wiki.
  * Completa el campo **_URL_** con la siguiente direccion:
```
https://android-robot-control.googlecode.com/svn/
```
  * Si sos participante del proyecto, deberas completar la informacion de autenticacion para que no sea solicitada posteriormente. El campo **_User_** es el nombre de usuario de tu cuenta de Google. El campo **_Password_** es generado por Google, y lo podes encontrar en la seccion **_Source_** > **_Checkout_** de este proyecto, siempre y cuando estes logueado en Google.
```
http://code.google.com/p/android-robot-control/source/checkout
```