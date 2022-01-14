# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../../lib")
sys.path.append("../../../../src")
from lib.ecr.reaccao import Reaccao
from lib.ecr.resposta import Resposta
from psa.accao import Mover
from psa.actuador import ESQ, DIR, FRT


class Contornar(Reaccao):

    def _detectar_estimulo(self, percepcao):
        return (percepcao[DIR].contacto and percepcao[DIR].obstaculo) \
               or (percepcao[ESQ].contacto and percepcao[ESQ].obstaculo)

    def _gerar_resposta(self, estimulo):
        accao = Mover(FRT)
        resposta = Resposta(accao)
        return resposta

