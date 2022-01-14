# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../../../../lib")
sys.path.append("../../../../../../src")

from lib.ecr.reaccao import Reaccao
from lib.ecr.resposta import Resposta
from psa.accao import Mover

GAMA = 0.9


class AproximarAlvoDir(Reaccao):

    def _detectar_estimulo(self, percepcao):#retorna um estimul
        if percepcao[self.direcao].alvo:
            return percepcao[self.direcao].distancia  # distancia ao alvo

    def __init__(self, direcao):
        self.direcao = direcao

    def _gerar_resposta(self,estimulo):
        prioridade = GAMA ** estimulo
        accao = Mover(self.direcao)
        resposta = Resposta(accao,prioridade)
        return resposta

    


