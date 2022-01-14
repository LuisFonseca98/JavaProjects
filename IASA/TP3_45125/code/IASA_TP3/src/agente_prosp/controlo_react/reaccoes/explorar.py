# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../../lib")
sys.path.append("../../../../src")

from lib.ecr.comportamento import Comportamento
from lib.ecr.resposta import Resposta
from random import choice
from psa.accao import Mover
from psa.actuador import FRT, ESQ, DIR


class Explorar(Comportamento):

    def activar(self, percepcao):
        # percepcao tem peso
        angulos = [ESQ, DIR, FRT]
        angulo = choice(angulos)
        accao = Mover(angulo)
        return Resposta(accao)
