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
from psa.actuador import FRT, ESQ, DIR
from psa.accao import Rodar


class EvitarObst(Reaccao):

    def _detectar_estimulo(self, percepcao):
        return percepcao[FRT].contacto and percepcao[FRT].obstaculo

    def _gerar_resposta(self, estimulo):
        accao = Rodar(DIR)
        resposta = Resposta(accao)
        return resposta

