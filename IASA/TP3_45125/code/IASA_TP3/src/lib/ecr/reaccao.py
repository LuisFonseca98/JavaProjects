# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../src")
from lib.ecr.comportamento import Comportamento


class Reaccao(Comportamento):

    def _detectar_estimulo(self, percepcao):
        raise NotImplementedError("Detetar estimulo Reaccao")

    def _gerar_resposta(self, estimulo):
        raise NotImplementedError("Gerar resposta Reaccao")

    def activar(self, percepcao):
        estimulo = self._detectar_estimulo(percepcao)
        if estimulo is not None and estimulo is not False:
            resposta = self._gerar_resposta(estimulo)
            return resposta
