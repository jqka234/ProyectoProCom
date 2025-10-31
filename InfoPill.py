import requests
import json
import tkinter as tk
from tkinter import messagebox



class FuentDatos:
    def obtenerdatos(self, nombre):
        raise NotImplementedError("Debes implementar este método en la subclase")

class ApiMedicamentos(FuentDatos):
    def __init__(self):
        self.__urlfda = "https://api.fda.gov/drug/event.json"
        self.__urltraductor = "https://api.mymemory.translated.net/get"

    def obtenerdatos(self, nombre):
        parametros = {"search": f"patient.drug.medicinalproduct:{nombre}", "limit": 1}
        r = requests.get(self.__urlfda, params=parametros)


        if r.status_code == 200:
            data = r.json()
        else:
            print("Error HTTP",r.status_code)
        resultados = data.get("results",[])
        if resultados:
            reacciones = resultados [0]["patient"]["reaction"]
            for r in reacciones:
                efectoin = r["reactionmeddrapt"]
                efecto = self.traductor(efectoin)
                print(efecto)
            
    def traductor(self, texto):
        parametro = {"q": texto, "langpair":"en|es"}
        r = requests.get(self.__urltraductor, params=parametro)

        if r.status_code == 200:
            data = r.json()
            return data["responseData"]["translatedText"]
        else:
            return texto
        
#falta el archivo json y el interfaz de tkinter

fuente = ApiMedicamentos()
fuente.obtenerdatos("ibuprofen")
