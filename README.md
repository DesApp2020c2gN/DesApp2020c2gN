[![Build Status](https://travis-ci.org/DesApp2020c2gN/DesApp2020c2gN.svg?branch=master)](https://travis-ci.org/DesApp2020c2gN/DesApp2020c2gN)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/bd5c2c52b7b94186aef44154fc6b9c50)](https://www.codacy.com/gh/DesApp2020c2gN/DesApp2020c2gN?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DesApp2020c2gN/DesApp2020c2gN&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/bd5c2c52b7b94186aef44154fc6b9c50)](https://www.codacy.com/gh/DesApp2020c2gN/DesApp2020c2gN?utm_source=github.com&utm_medium=referral&utm_content=DesApp2020c2gN/DesApp2020c2gN&utm_campaign=Badge_Coverage)

# Crowdfunding Solidario Argentina Conectada

Actualmente existen muchísimos pueblos y localidades que aún no cuentan con conectividad a Internet. El programa Argentina Conectada (luego rebautizado como Plan Federal de Internet) se propone llevar conectividad a todo el país, pueblo por pueblo.

Se nos encarga realizar una aplicación para ayudar al programa mediante crowdfunding (recaudación de fondos) solidario, en el que cualquier persona pueda ayudar a un pueblo en particular.

La aplicación debe consumir una API provista por ARSAT (<https://developers.arsat.apim.junar.com/6/plan-federal-de-internet>) para obtener la lista de localidades que aún no tienen internet. De cada localidad se obtiene el nombre, la provincia, la población y el estado actual de conectividad. En base a la población, la aplicación debe calcular el dinero necesario para proveer internet en la localidad, multiplicando dicho valor por un parámetro configurable por localidad (por default será $1000 por habitante y 100% para finalizar el proyecto).

Los parámetros a configurar (si fuera necesario) en los proyectos aún no iniciados son:
  - Factor: de 0 a $100.000
  - Porcentaje mínimo de cierre de proyecto: de 50% a 100%
  - Nombre de fantasía del proyecto: String a mostrar en el sitio
  - Fecha de Fin del proyecto.
  - Fecha de Inicio del proyecto

Por ejemplo, 
  - si la localidad tiene 1500 habitantes, y el factor está configurado en $2000, entonces el importe necesario para financiar la conectividad de la localidad es de 3 millones de pesos.
  - si la localidad tiene 1500 habitantes, y no tiene configuración de factor tomará el default que es $1000 por habitante.

Además, se crea un sistema de puntos por acciones solidarias que podrán ser canjeados por productos/servicios que dispondrá ARSAT en su momento. El esquema de puntos es el siguiente:
1) Si colabora en 1 proyecto con más de 1000 pesos, obtendrá la misma cantidad de puntos que pesos invertidos.
2) Si colabora en 1 proyecto de una localidad de menos de 2000 habitantes, la cantidad de puntos será el doble de los pesos invertidos.
3) Si colabora en más de 1 proyecto en el mes calendario, cuando realice su segunda colaboración, recibirá un bonus de 500 puntos.

Se debe mantener registro de los puntos ganados y cada usuario podrá verlos listados desde su perfil de usuario.

Es requisito indispensable proveer la capacidad de registrarse a la aplicación utilizando una cuenta existente en alguna de las herramientas o redes sociales más utilizadas (gmail, facebook, twitter, etc). No obstante, se debe proveer una opción de crear usuario donde se deba ingresar un nombre de usuario, email y contraseña. Además, durante el registro, el usuario debe proveer un apodo que se usará para preservar su identidad en los listados de donantes.

Los usuarios donantes, sólo pueden ver la lista de localidades, y al seleccionar una localidad, ver los detalles de la misma, que incluyen el nombre, la provincia, la población, el estado actual de conectividad, el total recaudado hasta el momento y el porcentaje faltante para completar lo necesario. Para cada localidad se mostrará un listado de donantes, con el apodo y donación de cada uno.

La aplicación permitirá realizar una donación para la localidad seleccionada, en la que se ingresa el monto a donar y un comentario.

La Home del sitio deberá listar :
  - Los proyectos abiertos: nombre, total de participantes, monto recaudado y porcentaje acumulado recaudado.
  - Los proyectos “Próximos a finalizar” (mes en curso):  nombre, total de participantes, monto recaudado y porcentaje acumulado recaudado.

Todos los días, se enviará un email automático a cada usuario registrado, que contenga dos listas, por un lado el top 10 de donaciones, para incentivar a otros usuarios; por otro lado el top 10 de localidades que hace más tiempo no reciben donaciones.

Los usuarios administradores, serán quienes decidan qué localidad ya completó su recaudación después de cumplirse el mínimo requerido para financiar la obra y la fecha establecida del fin del proyecto y avisar a los donantes las novedades, es decir, que gracias a su donación se iniciarán las obras en dicha localidad. Cuando esto ocurre, se le enviará un mail automático a cada donante.

La aplicación deberá ser completamente responsive ya que si bien ahora no vamos a contar con una aplicación mobile, será importante que pueda mostrarse correctamente en celulares y tablets.
