# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""

import sys
sys.path.append("../../../../lib")
sys.path.append("../../../../src")

from agente_prosp.controlo_react.reaccoes.evitar_obst import EvitarObst
from agente_prosp.controlo_react.reaccoes.explorar import Explorar
from agente_prosp.controlo_react.reaccoes.aproximar_alvo.aproximaralvo import AproximarAlvo
from lib.ecr.hierarquia import Hierarquia
from agente_prosp.controlo_react.reaccoes.contornar import Contornar


class Recolher(Hierarquia):

    def __init__(self):
        super().__init__([AproximarAlvo(), EvitarObst(), Contornar(), Explorar()])
