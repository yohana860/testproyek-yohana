package del.alstrudat;

import java.util.Locale;

public class Program {

    static class Soldier {
        String name;
        int strength;
        String division;
        String rank;
        int weight;

        Soldier(String name, int strength, String division, String rank) {
            this.name = name;
            this.strength = strength;
            this.division = division;
            this.rank = rank;
            this.weight = getRankWeight(rank);
        }

        private int getRankWeight(String rank) {
            switch (rank) {
                case "KNIGHT": return 1;
                case "SERGEANT": return 2;
                case "CAPTAIN": return 3;
                case "GENERAL": return 4;
                default: return 0;
            }
        }
    }

    static class DivisionReport {
        String name;
        String status;
        double score;
        int soldierCount;
        int priority;

        DivisionReport(String name, double score, int count) {
            this.name = name;
            this.score = score;
            this.soldierCount = count;
            setClassification(score);
        }

        private void setClassification(double s) {
            if (s > 100) { status = "CATASTROPHIC"; priority = 0; }
            else if (s > 50) { status = "CHAOS"; priority = 1; }
            else if (s > 20) { status = "MESSY"; priority = 2; }
            else if (s > 0) { status = "UNSTABLE"; priority = 3; }
            else { status = "ORDERED"; priority = 4; }
        }
    }

    public static void solve(int n, String[][] soldiersData) {
        Soldier[] soldiers = new Soldier[n];
        for (int i = 0; i < n; i++) {
            soldiers[i] = new Soldier(soldiersData[i][0], Integer.parseInt(soldiersData[i][1]), 
                                       soldiersData[i][2], soldiersData[i][3]);
        }

        // Step 1: Group divisions while maintaining original relative order (Stable Sort)
        stableMergeSort(soldiers, 0, n - 1);

        DivisionReport[] reports = new DivisionReport[n];
        int reportIdx = 0;

        int i = 0;
        while (i < n) {
            int j = i;
            double sumStrength = 0;
            while (j < n && soldiers[j].division.equals(soldiers[i].division)) {
                sumStrength += soldiers[j].strength;
                j++;
            }

            int count = j - i;
            Soldier[] divMembers = new Soldier[count];
            for (int k = 0; k < count; k++) {
                divMembers[k] = soldiers[i + k];
            }

            double weightedInv = countWeightedInversions(divMembers, 0, count - 1);
            double avgStrength = sumStrength / count;
            double chaosScore = (weightedInv * avgStrength) / (double) (count * count);
            if (count == 0) chaosScore = 0;

            reports[reportIdx++] = new DivisionReport(soldiers[i].division, chaosScore, count);
            i = j;
        }

        // Step 2: Final Report Sorting
        quickSortReports(reports, 0, reportIdx - 1);

        // Step 3: Print and KSI Calculation
        int ksi = 0;
        System.out.println("Divisi Status ChaosScore Prajurit");
        for (int k = 0; k < reportIdx; k++) {
            DivisionReport r = reports[k];
            System.out.printf(Locale.US, "%s %s %.2f %d\n", r.name, r.status, r.score, r.soldierCount);
            
            switch (r.status) {
                case "ORDERED": ksi += 3; break;
                case "CATASTROPHIC": ksi -= 5; break;
                case "CHAOS": ksi -= 3; break;
                case "MESSY": ksi -= 2; break;
                case "UNSTABLE": ksi -= 1; break;
            }
        }

        System.out.println("KSI: " + ksi);
        String kingdomStatus = (ksi > 0) ? "STABLE" : (ksi == 0 ? "CRITICAL" : "DOOMED");
        System.out.println("KINGDOM STATUS: " + kingdomStatus);
    }

    // --- HELPER METHODS ---

    private static void stableMergeSort(Soldier[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            stableMergeSort(arr, l, m);
            stableMergeSort(arr, m + 1, r);
            mergeByDivision(arr, l, m, r);
        }
    }

    private static void mergeByDivision(Soldier[] arr, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        Soldier[] L = new Soldier[n1], R = new Soldier[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int i = 0; i < n2; i++) R[i] = arr[m + 1 + i];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].division.compareTo(R[j].division) <= 0) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    private static double countWeightedInversions(Soldier[] arr, int l, int r) {
        double count = 0;
        if (l < r) {
            int m = l + (r - l) / 2;
            count += countWeightedInversions(arr, l, m);
            count += countWeightedInversions(arr, m + 1, r);
            count += mergeAndCount(arr, l, m, r);
        }
        return count;
    }

    private static double mergeAndCount(Soldier[] arr, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        Soldier[] L = new Soldier[n1], R = new Soldier[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int i = 0; i < n2; i++) R[i] = arr[m + 1 + i];

        double leftSumStrWeight = 0, leftSumWeight = 0;
        for (int k = 0; k < n1; k++) {
            leftSumStrWeight += (double) L[k].strength * L[k].weight;
            leftSumWeight += (double) L[k].weight;
        }

        double inversions = 0;
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].strength <= R[j].strength) {
                leftSumStrWeight -= (double) L[i].strength * L[i].weight;
                leftSumWeight -= (double) L[i].weight;
                arr[k++] = L[i++];
            } else {
                inversions += (double) R[j].weight * (leftSumStrWeight - (double) R[j].strength * leftSumWeight);
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
        return inversions;
    }

    private static void quickSortReports(DivisionReport[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortReports(arr, low, pi - 1);
            quickSortReports(arr, pi + 1, high);
        }
    }

    private static int partition(DivisionReport[] arr, int low, int high) {
        DivisionReport pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (compareReports(arr[j], pivot) <= 0) {
                i++;
                DivisionReport temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        DivisionReport temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static int compareReports(DivisionReport a, DivisionReport b) {
        if (a.priority != b.priority) return a.priority - b.priority;
        if (Math.abs(a.score - b.score) > 1e-9) return a.score > b.score ? -1 : 1;
        if (a.soldierCount != b.soldierCount) return a.soldierCount - b.soldierCount;
        return a.name.compareTo(b.name);
    }
}
//
