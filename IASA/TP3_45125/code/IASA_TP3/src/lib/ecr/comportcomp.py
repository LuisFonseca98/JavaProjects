# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../src")
from lib.ecr.comportamento import Comportamento


class ComportComp(Comportamento):

    def __init__(self, comportamentos):
        self.comportamentos = comportamentos

    """no enunciado esta public mas é protected
        pode levar underscore por convenção em python
        os metodos protected levam"""

    def selecionar_resposta(self, respostas):
        raise NotImplementedError("selecionar_resposta ComportComp")

    def activar(self, percepcao):
        listaRespostas = []
        # o 1º comportamento tem mais prioridade e por assim seguinte
        for comportamento in self.comportamentos:
            # é ativada um comportamento depedendo da percepcao
            resposta = comportamento.activar(percepcao)

            if resposta is not None: listaRespostas.append(resposta)

        if len(listaRespostas) > 0:
            resp = self.selecionar_resposta(listaRespostas)
            return resp
