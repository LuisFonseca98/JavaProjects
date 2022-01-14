# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020
@author: luisc
"""
import sys

sys.path.append("../../lib")
from psa.agente import Agente

class AgenteProspector(Agente):

    def __init__(self, controlo):
        self.controlo = controlo

    def executar(self):
        percepcao = self.__percepcionar()
        accao = self.__processar(percepcao)
        self.__actuar(accao)

    def __percepcionar(self):
        return self.sensor_multiplo.detectar()

    def __processar(self, percepcao):
        return self.controlo.processar(percepcao)

    def __actuar(self, accao):
        if accao is not None:
            return self.actuador.actuar(accao)
