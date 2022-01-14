# -*- coding: utf-8 -*-
"""
Created on Tue May  5 14:18:31 2020

@author: luisc
"""
import sys
sys.path.append("../../../../../lib")
sys.path.append("../../../../../src")

from agente_prosp.controlo_react.reaccoes.aproximar_alvo.aproximar_alvo_dir.aproximar_alvo_dir import AproximarAlvoDir
from lib.ecr.prioridade import Prioridade
from psa.actuador import FRT,ESQ,DIR


class AproximarAlvo(Prioridade):

    def __init__(self):
        super().__init__([AproximarAlvoDir(ESQ),AproximarAlvoDir(FRT),AproximarAlvoDir(DIR)])
