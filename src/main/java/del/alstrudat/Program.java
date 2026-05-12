package del.alstrudat;

public class Program {

  /**
   * THE INVERTED KINGDOM: WAR OF CHAOS
   *
   * Diberikan N prajurit dengan format: nama kekuatan divisi rank
   * Rank: KNIGHT=1, SERGEANT=2, CAPTAIN=3, GENERAL=4
   *
   * TAHAP 1 — Weighted Inversion Count per Divisi (gunakan MERGE SORT yang dimodifikasi)
   *   Untuk setiap pasangan (i,j) dalam divisi yang sama di mana i < j dan kekuatan[i] > kekuatan[j]:
   *   weighted_inv += (kekuatan[i] - kekuatan[j]) * rank_weight[i] * rank_weight[j]
   *   PENTING: urutan prajurit dalam divisi mengikuti urutan kemunculan di input.
   *
   * TAHAP 2 — Chaos Score per Divisi
   *   avg_kekuatan = rata-rata kekuatan seluruh anggota divisi
   *   chaos_score  = (weighted_inv * avg_kekuatan) / (n_divisi * n_divisi)
   *
   * TAHAP 3 — Klasifikasi
   *   score > 100        → CATASTROPHIC
   *   50 < score ≤ 100   → CHAOS
   *   20 < score ≤ 50    → MESSY
   *   0  < score ≤ 20    → UNSTABLE
   *   score = 0          → ORDERED
   *
   *   Kingdom Stability Index (KSI):
   *   KSI = (jml ORDERED × 3) - (jml CATASTROPHIC × 5) - (jml CHAOS × 3)
   *         - (jml MESSY × 2) - (jml UNSTABLE × 1)
   *
   *   KINGDOM STATUS:
   *   KSI > 0 → STABLE | KSI = 0 → CRITICAL | KSI < 0 → DOOMED
   *
   * TAHAP 4 — Quick Sort Laporan (multi-key):
   *   1. Status (CATASTROPHIC → CHAOS → MESSY → UNSTABLE → ORDERED)
   *   2. Jika sama → chaos_score DESCENDING
   *   3. Jika sama → jumlah prajurit ASCENDING
   *   4. Jika sama → nama divisi ALPHABETICALLY
   *
   * FORMAT OUTPUT:
   *   Divisi Status ChaosScore Prajurit
   *   [nama] [status] [score 2 desimal] [jumlah]
   *   ...
   *   KSI: [nilai]
   *   KINGDOM STATUS: [STABLE/CRITICAL/DOOMED]
   *
   * CONSTRAINT:
   *   - Wajib implementasi Merge Sort manual untuk counting inversion (array-based)
   *   - Wajib implementasi Quick Sort manual untuk sorting laporan
   *   - Dilarang menggunakan Collections.sort(), Arrays.sort(), atau library sort apapun
   */
  public static void solve(int n, String[][] soldiers) {
    // TODO: Implementasikan solusi di sini
  }
}
