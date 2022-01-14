# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""

"""Representa uma resposta a um determinado comportamento"""


class Resposta():

    def __init__(self, accao, prioridade=0):
        self.__accao = accao
        self.__prioridade = prioridade

    @property
    def accao(self):
        return self.__accao

    @property
    def prioridade(self):
        #cada resposta tem a sua prioridade
        return self.__prioridade
