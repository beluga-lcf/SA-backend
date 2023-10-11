import psycopg2
import requests
import pandas as pd
import subprocess
import boto3

DATABASE=""
USERNAME=""
PASSWORD=""
HOST=""
PORT=""
def create_schema():
    conn = psycopg2.connect(database=DATABASE, user=USERNAME, password=PASSWORD, host=HOST, port=PORT)
    cur = conn.cursor()
    sql_url = 'https://raw.githubusercontent.com/OpenAlexOrg/openalex-schema/main/schema.sql'
    schema_sql = requests.get(sql_url).text
    cur.execute(schema_sql)
    conn.commit()
    cur.close()
    conn.close()


def download_and_run_script():
    s3 = boto3.client('s3', config=botocore.client.Config(signature_version=botocore.UNSIGNED))
    bucket_name = 'openalex'
    local_directory = 'openalex-snapshot'
    subprocess.run(['mkdir', '-p', local_directory])

    for obj in s3.list_objects_v2(Bucket=bucket_name)['Contents']:
        filename = obj['Key']
        local_filename = f'{local_directory}/{filename}'
        s3.download_file(bucket_name, filename, local_filename)

    script_url = 'https://raw.githubusercontent.com/OpenAlexOrg/openalex-schema/main/flatten-openalex-jsonl.py'
    script_content = requests.get(script_url).text
    with open('flatten-openalex-jsonl.py', 'w') as file:
        file.write(script_content)
    subprocess.run(['mkdir', '-p', 'csv-files'])
    subprocess.run(['python3', 'flatten-openalex-jsonl.py'])


def load_csv_to_db():
    conn = psycopg2.connect(database=DATABASE, user=USERNAME, password=PASSWORD, host=HOST, port=PORT)
    data = pd.read_csv('csv-files/your_file.csv')
    data.to_sql('your_table_name', conn, if_exists='replace', index=False)
    conn.close()

def run_queries():
    conn = psycopg2.connect(database=DATABASE, user=USERNAME, password=PASSWORD, host=HOST, port=PORT)
    cur = conn.cursor()
    query = "SELECT * FROM table_name LIMIT 10;"#TODO
    cur.execute(query)
    results = cur.fetchall()
    for row in results:
        print(row)
    cur.close()
    conn.close()

if __name__ == "__main__":
    create_schema()
    download_and_run_script()
    load_csv_to_db()
    run_queries()
