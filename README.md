# Simulasi Race Condition Dan Pendekatan Solusi

- Java 17
- Spring Boot
- Rest API
- Spring Data JPA
- Lombok
- H2DB
- Synchronized

## Race Condition

![image](https://github.com/user-attachments/assets/0e029b0d-723a-4b60-bf6e-0d377e2e58bc)

Dua proses (setoran 300 dan penarikan 200) terjadi secara bersamaan, \
menyebabkan hasil akhir saldo yang tidak sesuai dengan ekspektasi. \
Hasil akhirnya adalah 800, sedangkan yang diharapkan adalah 1100. \
\
Hal ini terjadi karena kedua proses membaca saldo awal yang sama (1000) secara bersamaan tanpa sinkronisasi.


### Bagaimana menjalankan aplikasi
- Git clone
```
$git clone https://github.com/n0tx/race-condition.git
```
- Run Spring Boot
```
$cd race-condition
$pwd
/race-condition
$./mvnw spring-boot:run
```

### Inisialisasi data rekening baru dan nilai saldo awal

![image](https://github.com/user-attachments/assets/908bbe3b-936a-455c-b3bc-9905a02d387d)


### Melihat data dari h2db console
```
http://localhost:8080/h2-ui
```

![image](https://github.com/user-attachments/assets/579ff3ff-0855-4d6c-b1fc-d85faa40708e)

![image](https://github.com/user-attachments/assets/514e3166-a853-4be5-a405-4e24c48bf784)


## Pendekatan Solusi
- Menjalankan ulang aplikasi agar data kembali seperti semula

![image](https://github.com/user-attachments/assets/908bbe3b-936a-455c-b3bc-9905a02d387d)

- Melakukan 2 permintaan proses (setoran 300 dan penarikan 200) yang dilakukan secara bersamaan \
dan mendapatkan hasil yang sesuai dengan ekspektasi

![image](https://github.com/user-attachments/assets/f893ed84-9179-45d0-b336-a34375fa3041)

## Postman Collection
Import file di bawah untuk pengujian mengggunakan postman

- Simulasi Race Condition Tanpa Synchronized
```
{
	"info": {
		"_postman_id": "463b853f-9b0d-4a54-9b75-2448b8b93ce9",
		"name": "Simulasi Race Condition Tanpa Sync",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "18173535"
	},
	"item": [
		{
			"name": "Setor Tarik Parallel Request",
			"event": [
				{
					"listen": "prerequest",
					"script": {
						"exec": [
							"pm.sendRequest({",
							"    url: 'http://localhost:8080/api/rekening/setorTanpaSync/1?totalSetor=300',",
							"    method: 'POST'",
							"}, function (err, res) {",
							"    console.log(\"Permintaan Setor dikirim\");",
							"});",
							"",
							"pm.sendRequest({",
							"    url: 'http://localhost:8080/api/rekening/tarikTanpaSync/1?totalTarik=200',",
							"    method: 'POST'",
							"}, function (err, res) {",
							"    console.log(\"Permintaan Tarik dikirim\");",
							"});",
							""
						],
						"type": "text/javascript",
						"packages": {}
					}
				}
			],
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/rekening/saldo/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"rekening",
						"saldo",
						"1"
					]
				}
			},
			"response": []
		}
	]
}
```


- Simulasi Race Condition Dengan Synchronized
```
{
	"info": {
		"_postman_id": "cde9ff98-16df-4d71-9a0e-5f623abba93f",
		"name": "Simulasi Race Condition Dengan Sync",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "18173535"
	},
	"item": [
		{
			"name": "Setor Tarik Parallel Request",
			"event": [
				{
					"listen": "prerequest",
					"script": {
						"exec": [
							"pm.sendRequest({",
							"    url: 'http://localhost:8080/api/rekening/setorDenganSync/1?totalSetor=300',",
							"    method: 'POST'",
							"}, function (err, res) {",
							"    console.log(\"Permintaan Setor dikirim\");",
							"});",
							"",
							"pm.sendRequest({",
							"    url: 'http://localhost:8080/api/rekening/tarikDenganSync/1?totalTarik=200',",
							"    method: 'POST'",
							"}, function (err, res) {",
							"    console.log(\"Permintaan Tarik dikirim\");",
							"});",
							""
						],
						"type": "text/javascript",
						"packages": {}
					}
				}
			],
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/api/rekening/saldo/1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"rekening",
						"saldo",
						"1"
					]
				}
			},
			"response": []
		}
	]
}   
```
