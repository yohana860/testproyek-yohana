# alstrudat-cXX-USERNAME

## Description

Implementasikan sistem **Median dari Stream Data** menggunakan **dua Heap** (Max-Heap dan Min-Heap) secara manual (array-based, tanpa PriorityQueue Java), sekaligus tampilkan **K elemen terbesar** menggunakan Min-Heap.

### Detail Soal

Diberikan sebuah stream bilangan bulat sebanyak **N** elemen. Setelah setiap elemen dimasukkan, cetak **median** dari semua elemen yang telah masuk sejauh ini:
- Jika jumlah elemen **ganjil**: cetak bilangan bulat (integer)
- Jika jumlah elemen **genap**: cetak rata-rata dua tengah sebagai **double dengan 1 desimal** (contoh: `4.0`, `3.5`)

Setelah seluruh elemen diproses, cetak **K elemen terbesar** dalam urutan menurun pada baris terakhir.

### Format Input

```
N
elemen_1
elemen_2
...
elemen_N
K
```

### Format Output

```
median_setelah_elemen_1
median_setelah_elemen_2
...
median_setelah_elemen_N
K Largest: x1 x2 ... xK
```

### Constraint

- 1 ≤ N ≤ 10⁴
- 1 ≤ K ≤ N
- -10⁶ ≤ elemen ≤ 10⁶
- **Wajib menggunakan Heap manual (array-based)**, bukan PriorityQueue Java

## Source Codes

| No | File         | Deskripsi                                         |
|----|--------------|---------------------------------------------------|
| 1  | App.java     | Entry point, membaca input                        |
| 2  | Program.java | Implementasikan `solve()` menggunakan dua Heap    |

## Test Cases

| No | Input (N, stream, K)                          | Output Akhir (Medians + K Largest)                                              |
|----|-----------------------------------------------|---------------------------------------------------------------------------------|
| 1  | N=5, [5,3,8,1,9], K=3                         | 5, 4.0, 5, 4.0, 5 → K Largest: 9 8 5                                           |
| 2  | N=2, [2,4], K=1                               | 2, 3.0 → K Largest: 4                                                           |
| 3  | N=1, [7], K=1                                 | 7 → K Largest: 7                                                                |
| 4  | N=4, [10,10,10,10], K=2                       | 10, 10.0, 10, 10.0 → K Largest: 10 10                                          |
| 5  | N=10, [1..10], K=4                            | 1,1.5,2,...,5.5 → K Largest: 10 9 8 7                                          |
| 6  | N=10, [10..1], K=3                            | 10,9.5,9,...,5.5 → K Largest: 10 9 8                                           |
| 7  | N=6, [100,1,50,25,75,10], K=2                 | 100,50.5,50,37.5,50,37.5 → K Largest: 100 75                                   |
| 8  | N=5, [-5,-1,-3,-2,-4], K=2                    | -5,-3.0,-3,-2.5,-3 → K Largest: -1 -2                                          |
| 9  | N=10, [3,1,4,1,5,9,2,6,5,3], K=5             | 3,2.0,3,2.0,3,3.5,3,3.5,4,3.5 → K Largest: 9 6 5 5 4                         |
| 10 | N=10, [1000,500,750,250,875,125,625,375,812,438], K=4 | ... → K Largest: 1000 875 812 750                                    |

## Compile

```bash
mvn clean package
```

## Run

```bash
java -cp target/app.jar del.alstrudat.App
```
