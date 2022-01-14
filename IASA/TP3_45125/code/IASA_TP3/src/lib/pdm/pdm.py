class PDM():
    def __init__(self, gama, delta_max):
        self.__gama = gama
        self.__delta_max = delta_max

    def utilidade(self, modelo):  # retorna utilidade
        # modelo PDM
        U = {s: 0 for s in modelo.S()}  # dicionario com key = s e value = R
        while True:
            Uant = U.copy()
            delta = 0
            # a utilidade de um estado é o valor da ação que maximiza
            # a função cumulativa de todas as transições
            for s in modelo.S():
                U[s] = max(self.util_accao(s, a, Uant, modelo)  # U[s] = valor
                           for a in modelo.A(s))
                delta = max(delta, abs(U[s] - Uant[s]))

            if delta < self.__delta_max:
                break
        return U

    def util_accao(self, s, a, U, modelo):  # retorna double
        # s estado
        # a accao
        # U utilidade
        # modelo MODELO-PDM
        T = modelo.T
        R = modelo.R
        gama = self.__gama
        # nesta lista retorna se a probabilidade de cada 1 dos estados
        return sum(p * (R(s, a, sn) + self.__gama * U[sn])
                   for (p, sn) in T(s, a))

    def politica(self, U, modelo):  # Retorna politica
        # para cada estado escolhe se a ação que leva a maior utilidade
        # U utilidade
        # modelo MODELO-PDM
        politicas = {}
        for s in modelo.S():
            politicas[s] = max(modelo.A(s), key=lambda a: self.util_accao(s, a, U, modelo))
        return politicas

    def resolver(self, modelo):
        # modelo MODELO-PDM
        U = self.utilidade(modelo)
        P = self.politica(U, modelo)
        return U, P
