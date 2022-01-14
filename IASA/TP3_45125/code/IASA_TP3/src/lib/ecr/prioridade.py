# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../src")
from lib.ecr.comportcomp import ComportComp


class Prioridade(ComportComp):

    def __init__(self,comportamentos):
        super().__init__(comportamentos)

    """Retorna a resposta com maior prioridade"""
    def selecionar_resposta(self, respostas):
        respostaMaxima = respostas[0]

        for resposta in respostas:
            if resposta is not None and respostaMaxima is not None:
                if resposta.prioridade > respostaMaxima.prioridade:
                    respostaMaxima = resposta

        return respostaMaxima
        #return max(respostas, key=lambda resposta: resposta.prioridade)
