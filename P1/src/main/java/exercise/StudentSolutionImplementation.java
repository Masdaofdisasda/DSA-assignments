package main.java.exercise;

import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "David", // Vorname
                "Köppl", // Nachname
                "12022493" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für die Indexsuche
    public int findIndex(int[] numbers, int element) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == element) {
                return i;
            }
        }

        return -1;
    }

    // Implementieren Sie hier den Gale-Shapley-Algorithmus
    public void performGaleShapley(StableMatchingInstance instance, StableMatchingSolution solution) {
        // next array für noch nicht gewählte familien
        int[] next = new int[instance.getN()];
        for (int i = 0; i < next.length; i++) {
            next[i] = 0;
        }
        //instance.print();

        // solange es freie Kinder gibt
        while (solution.hasUnassignedChildren()){
            // wähle nächste freie Kind
            int child = solution.getNextUnassignedChild();
            // wähle nächste Familie in Präferenzliste
            int family = instance.getFamilyOfChildAtRank(child, next[child]);
            next[child]++;

            // ist die Familie frei, bilde ein Paar
            if (solution.isFamilyFree(family)){
                solution.assign(child, family);
            // sonst bevorzugt Familie potentiellen Paar < aktuellen Paar, bilde Paar
            } else if (instance.getRankOfChildForFamily(family, child) < instance.getRankOfChildForFamily(family, solution.getAssignedChild(family))){
                solution.setFree(solution.getAssignedChild(family));
                solution.assign(child, family);
            }
            //wähle nächstes Kind oder wenn Familie Kind abweist, wähle nächste Familie in next
        }

    }

    // Implementieren Sie hier Ihre Methode zur Überprüfung, ob ein Matching stabil ist.
    public boolean isStableMatching(StableMatchingInstance instance, StableMatchingSolution solution) {

        int N = instance.getN();

        // alle Kinder haben Familie
        if (solution.hasUnassignedChildren()){
            return false;
        }

        // wähle Kinder 1 bis n
        for (int child = 0; child < N; child++) {
            // Ist Kind mit Favorit gepaart?
            int rankFam = instance.getRankOfFamilyForChild(child, solution.getAssignedFamily(child));
            if (rankFam > 0){
                // nein -> gehe alle höher gereihten Paare durch
                for (int j = rankFam-1; j >= 0; j--) {
                    int family = instance.getFamilyOfChildAtRank(child,j);
                    // aktuelles Paar > potentielles Paar -> instabil :(
                    if (instance.getRankOfChildForFamily(family, child) < instance.getRankOfChildForFamily(family, solution.getAssignedChild(family))){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
