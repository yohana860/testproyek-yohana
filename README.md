# alstrudat-c01-USERNAME

## Description

**THE INVERTED KINGDOM: WAR OF CHAOS**

Diberikan N prajurit dengan atribut `nama`, `kekuatan`, `divisi`, dan `rank`. Implementasikan sistem analisis kekacauan kerajaan menggunakan **Merge Sort** (weighted inversion) dan **Quick Sort** (multi-key sorting).

### Rank Weight
| Rank | Weight |
|------|--------|
| KNIGHT | 1 |
| SERGEANT | 2 |
| CAPTAIN | 3 |
| GENERAL | 4 |

### Tahap 1 — Weighted Inversion Count (Merge Sort)
Untuk setiap pasangan (i,j) dalam divisi yang sama di mana `i < j` dan `kekuatan[i] > kekuatan[j]`:
```
weighted_inv += (kekuatan[i] - kekuatan[j]) × rank_weight[i] × rank_weight[j]
```
Urutan prajurit dalam divisi = urutan kemunculan di input.

### Tahap 2 — Chaos Score
```
chaos_score = (weighted_inv × avg_kekuatan) / (n_divisi²)
```

### Tahap 3 — Klasifikasi
| Score | Status |
|-------|--------|
| > 100 | CATASTROPHIC |
| 50 < s ≤ 100 | CHAOS |
| 20 < s ≤ 50 | MESSY |
| 0 < s ≤ 20 | UNSTABLE |
| = 0 | ORDERED |

```
KSI = (ORDERED×3) - (CATASTROPHIC×5) - (CHAOS×3) - (MESSY×2) - (UNSTABLE×1)
KINGDOM STATUS: STABLE (KSI>0) | CRITICAL (KSI=0) | DOOMED (KSI<0)
```

### Tahap 4 — Quick Sort Laporan (multi-key)
1. Status: CATASTROPHIC → CHAOS → MESSY → UNSTABLE → ORDERED
2. Chaos Score **descending**
3. Jumlah prajurit **ascending**
4. Nama divisi **alphabetically**

### Format Input
```
N
nama kekuatan divisi rank
...
```

### Format Output
```
Divisi Status ChaosScore Prajurit
[nama] [status] [score 2 desimal] [jumlah]
...
KSI: [nilai]
KINGDOM STATUS: [STABLE/CRITICAL/DOOMED]
```

### Constraint
- 1 ≤ N ≤ 10.000
- **WAJIB** implementasi Merge Sort manual untuk weighted inversion
- **WAJIB** implementasi Quick Sort manual untuk sorting laporan
- **DILARANG** menggunakan Collections.sort(), Arrays.sort(), atau library sort apapun

## Source Codes

| No | File | Deskripsi |
|----|------|-----------|
| 1 | App.java | Entry point, membaca input |
| 2 | Program.java | Implementasikan `solve()` di sini |

## Test Cases

| No | Skenario | N | Divisi |
|----|----------|---|--------|
| 1 | Warmup — 2 divisi kecil | 6 | 2 |
| 2 | Single divisi — semua prajurit satu divisi | 10 | 1 |
| 3 | Semua divisi berbeda — setiap prajurit sendiri | 8 | 8 |
| 4 | Kekuatan identik semua — edge case inversion=0 | 8 | 2 |
| 5 | Already sorted ascending — semua ORDERED | 10 | 2 |
| 6 | Perfectly reversed descending — semua CATASTROPHIC | 10 | 2 |
| 7 | **N=5000** — brute force O(n²) timeout | 5000 | 5 |
| 8 | Chaos Score tepat di batas klasifikasi | 16 | 5 |
| 9 | 10 divisi semua CHAOS — uji 4 kunci Quick Sort | 20 | 10 |
| 10 | **Full Chaos** — N=3024, 15 divisi, semua status | 3024 | 15 |

## Compile
```bash
mvn clean package
```

## Run
```bash
java -cp target/app.jar del.alstrudat.App
```
