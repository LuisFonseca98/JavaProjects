# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../src")
from lib.ecr.comportcomp import ComportComp


class Hierarquia(ComportComp):

    def __init__(self, comportamentos):
        super().__init__(comportamentos)

    """Retorna a resposta com mais prioridade, neste caso a primeira
       contem uma lista ordenada por ordem de prioridade"""
    def selecionar_resposta(self, respostas):
        return respostas[0]
