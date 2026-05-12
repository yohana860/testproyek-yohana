package del.alstrudat;
import java.util.HashMap;
import java.util.ArrayList;

public class Program {

  // =========================================================================
  // RANK WEIGHT
  // =========================================================================
  static int rankWeight(String rank) {
    switch (rank) {
      case "KNIGHT":   return 1;
      case "SERGEANT": return 2;
      case "CAPTAIN":  return 3;
      case "GENERAL":  return 4;
      default:         return 0;
    }
  }

  // =========================================================================
  // TAHAP 1: MERGE SORT — Weighted Inversion Count
  // Setiap elemen array adalah long[2] = {kekuatan, rank_weight}
  // =========================================================================
  static long mergeAndCount(long[][] arr, int left, int right) {
    if (right - left <= 1) return 0;

    int mid = (left + right) / 2;
    long inv = 0;
    inv += mergeAndCount(arr, left, mid);
    inv += mergeAndCount(arr, mid, right);

    // Merge dua bagian: [left..mid) dan [mid..right)
    long[][] temp = new long[right - left][2];
    int i = left, j = mid, k = 0;

    while (i < mid && j < right) {
      if (arr[i][0] <= arr[j][0]) {
        // Tidak ada inversion dari pasangan ini
        temp[k++] = arr[i++];
      } else {
        // arr[i] > arr[j]: semua elemen arr[i..mid) membentuk inversion dengan arr[j]
        for (int x = i; x < mid; x++) {
          inv += (arr[x][0] - arr[j][0]) * arr[x][1] * arr[j][1];
        }
        temp[k++] = arr[j++];
      }
    }

    while (i < mid) temp[k++] = arr[i++];
    while (j < right) temp[k++] = arr[j++];

    // Copy kembali ke arr
    for (int t = 0; t < temp.length; t++) {
      arr[left + t] = temp[t];
    }

    return inv;
  }

  // =========================================================================
  // TAHAP 4: QUICK SORT — Multi-key untuk laporan divisi
  // Setiap report: String[] = {divName, status, chaosScoreStr, jumlahStr}
  // Sort key:
  //   1. Status order: CATASTROPHIC=0, CHAOS=1, MESSY=2, UNSTABLE=3, ORDERED=4
  //   2. Chaos score DESCENDING
  //   3. Jumlah prajurit ASCENDING
  //   4. Nama divisi ALPHABETICALLY
  // =========================================================================
  static int statusOrder(String status) {
    switch (status) {
      case "CATASTROPHIC": return 0;
      case "CHAOS":        return 1;
      case "MESSY":        return 2;
      case "UNSTABLE":     return 3;
      case "ORDERED":      return 4;
      default:             return 5;
    }
  }

  // Comparator: return negatif jika a < b (a harus duluan)
  static int compareReport(double[] scores, int[] jumlah, String[] names, String[] statuses,
                            int a, int b) {
    // Key 1: status ascending
    int so = statusOrder(statuses[a]) - statusOrder(statuses[b]);
    if (so != 0) return so;

    // Key 2: chaos score descending
    if (scores[a] > scores[b]) return -1;
    if (scores[a] < scores[b]) return  1;

    // Key 3: jumlah prajurit ascending
    int jo = jumlah[a] - jumlah[b];
    if (jo != 0) return jo;

    // Key 4: nama divisi alphabetically
    return names[a].compareTo(names[b]);
  }

  static void quickSort(double[] scores, int[] jumlah, String[] names, String[] statuses,
                        int low, int high) {
    if (low >= high) return;

    // Pilih pivot = elemen tengah (mengurangi worst-case pada data terurut)
    int mid = (low + high) / 2;
    // Swap pivot ke posisi high
    swap(scores, jumlah, names, statuses, mid, high);

    int pivotIdx = high;
    int i = low - 1;

    for (int j = low; j < high; j++) {
      if (compareReport(scores, jumlah, names, statuses, j, pivotIdx) <= 0) {
        i++;
        swap(scores, jumlah, names, statuses, i, j);
      }
    }
    swap(scores, jumlah, names, statuses, i + 1, high);

    int p = i + 1;
    quickSort(scores, jumlah, names, statuses, low, p - 1);
    quickSort(scores, jumlah, names, statuses, p + 1, high);
  }

  static void swap(double[] scores, int[] jumlah, String[] names, String[] statuses, int a, int b) {
    double tmpD = scores[a]; scores[a] = scores[b]; scores[b] = tmpD;
    int    tmpI = jumlah[a]; jumlah[a] = jumlah[b]; jumlah[b] = tmpI;
    String tmpS = names[a];  names[a]  = names[b];  names[b]  = tmpS;
           tmpS = statuses[a]; statuses[a] = statuses[b]; statuses[b] = tmpS;
  }

  // =========================================================================
  // TAHAP 3: Klasifikasi Chaos Score
  // =========================================================================
  static String classify(double score) {
    if (score > 100) return "CATASTROPHIC";
    if (score > 50)  return "CHAOS";
    if (score > 20)  return "MESSY";
    if (score > 0)   return "UNSTABLE";
    return "ORDERED";
  }

  // =========================================================================
  // MAIN SOLVE
  // =========================================================================
  public static void solve(int n, String[][] soldiers) {

    // --- Kumpulkan prajurit per divisi (urutan sesuai input) ---
    // Gunakan LinkedHashMap agar urutan divisi pertama kali muncul terjaga
    // (tidak mempengaruhi output karena kita sort, tapi rapi)
    HashMap<String, ArrayList<long[]>> divMap = new HashMap<>();
    // Simpan urutan kemunculan divisi
    ArrayList<String> divOrder = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      String nama     = soldiers[i][0];
      int    kekuatan = Integer.parseInt(soldiers[i][1]);
      String divisi   = soldiers[i][2];
      String rank     = soldiers[i][3];
      int    rw       = rankWeight(rank);

      if (!divMap.containsKey(divisi)) {
        divMap.put(divisi, new ArrayList<>());
        divOrder.add(divisi);
      }
      divMap.get(divisi).add(new long[]{kekuatan, rw});
    }

    int numDiv = divOrder.size();

    // Array laporan per divisi
    String[] divNames  = new String[numDiv];
    String[] statuses  = new String[numDiv];
    double[] scores    = new double[numDiv];
    int[]    jumlah    = new int[numDiv];

    // --- TAHAP 1 & 2: Hitung weighted inversion dan chaos score per divisi ---
    for (int d = 0; d < numDiv; d++) {
      String divName = divOrder.get(d);
      ArrayList<long[]> members = divMap.get(divName);
      int nd = members.size();

      // Salin ke array untuk merge sort
      long[][] arr = new long[nd][2];
      long sumKekuatan = 0;
      for (int i = 0; i < nd; i++) {
        arr[i][0] = members.get(i)[0]; // kekuatan
        arr[i][1] = members.get(i)[1]; // rank_weight
        sumKekuatan += arr[i][0];
      }

      // Weighted inversion via Merge Sort
      long weightedInv = mergeAndCount(arr, 0, nd);

      double avgKekuatan = (double) sumKekuatan / nd;
      double chaosScore  = (weightedInv * avgKekuatan) / ((double) nd * nd);

      divNames[d] = divName;
      scores[d]   = chaosScore;
      jumlah[d]   = nd;
      statuses[d] = classify(chaosScore);
    }

    // --- TAHAP 3: Hitung KSI ---
    int ksi = 0;
    for (int d = 0; d < numDiv; d++) {
      switch (statuses[d]) {
        case "ORDERED":       ksi += 3;  break;
        case "CATASTROPHIC":  ksi -= 5;  break;
        case "CHAOS":         ksi -= 3;  break;
        case "MESSY":         ksi -= 2;  break;
        case "UNSTABLE":      ksi -= 1;  break;
      }
    }

    String kingdomStatus;
    if (ksi > 0)      kingdomStatus = "STABLE";
    else if (ksi == 0) kingdomStatus = "CRITICAL";
    else              kingdomStatus = "DOOMED";

    // --- TAHAP 4: Quick Sort laporan ---
    quickSort(scores, jumlah, divNames, statuses, 0, numDiv - 1);

    // --- OUTPUT ---
    StringBuilder sb = new StringBuilder();
    sb.append("Divisi Status ChaosScore Prajurit\n");
    for (int d = 0; d < numDiv; d++) {
      sb.append(divNames[d])
        .append(" ")
        .append(statuses[d])
        .append(" ")
        .append(String.format("%.2f", scores[d]))
        .append(" ")
        .append(jumlah[d])
        .append("\n");
    }
    sb.append("KSI: ").append(ksi).append("\n");
    sb.append("KINGDOM STATUS: ").append(kingdomStatus);

    System.out.print(sb);
  }
}
