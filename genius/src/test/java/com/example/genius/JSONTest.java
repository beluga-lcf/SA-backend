package com.example.genius;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JSONTest {
    @Test
    public void getArrayTest() throws JSONException {
        String jsonString = "{\n" +
                "    \"id\": \"https://openalex.org/A5023888391\",\n" +
                "    \"orcid\": \"https://orcid.org/0000-0001-6187-6610\",\n" +
                "    \"display_name\": \"Jason Priem\",\n" +
                "    \"display_name_alternatives\": [\n" +
                "        \"Jason Priem\",\n" +
                "        \"Priem Jason\"\n" +
                "    ],\n" +
                "    \"works_count\": 52,\n" +
                "    \"cited_by_count\": 2184,\n" +
                "    \"summary_stats\": {\n" +
                "        \"2yr_mean_citedness\": 0.5,\n" +
                "        \"h_index\": 16,\n" +
                "        \"i10_index\": 17\n" +
                "    },\n" +
                "    \"ids\": {\n" +
                "        \"openalex\": \"https://openalex.org/A5023888391\",\n" +
                "        \"orcid\": \"https://orcid.org/0000-0001-6187-6610\",\n" +
                "        \"scopus\": \"http://www.scopus.com/inward/authorDetails.url?authorID=36455008000&partnerID=MN8TOARS\"\n" +
                "    },\n" +
                "    \"last_known_institution\": {\n" +
                "        \"id\": \"https://openalex.org/I4200000001\",\n" +
                "        \"ror\": \"https://ror.org/02nr0ka47\",\n" +
                "        \"display_name\": \"OurResearch\",\n" +
                "        \"country_code\": \"CA\",\n" +
                "        \"type\": \"nonprofit\",\n" +
                "        \"lineage\": [\n" +
                "            \"https://openalex.org/I4200000001\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"x_concepts\": [\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C41008148\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q21198\",\n" +
                "            \"display_name\": \"Computer science\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 98.1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C136764020\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q466\",\n" +
                "            \"display_name\": \"World Wide Web\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 73.1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C17744445\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q36442\",\n" +
                "            \"display_name\": \"Political science\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 69.2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C161191863\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q199655\",\n" +
                "            \"display_name\": \"Library science\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 65.4\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C199539241\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q7748\",\n" +
                "            \"display_name\": \"Law\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 61.5\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C2522767166\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q2374463\",\n" +
                "            \"display_name\": \"Data science\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 59.6\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C2778407487\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q14565201\",\n" +
                "            \"display_name\": \"Altmetrics\",\n" +
                "            \"level\": 2,\n" +
                "            \"score\": 42.3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C111919701\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q9135\",\n" +
                "            \"display_name\": \"Operating system\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 36.5\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C121332964\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q413\",\n" +
                "            \"display_name\": \"Physics\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 34.6\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C144024400\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q21201\",\n" +
                "            \"display_name\": \"Sociology\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 32.7\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C33923547\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q395\",\n" +
                "            \"display_name\": \"Mathematics\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 28.8\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C142362112\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q735\",\n" +
                "            \"display_name\": \"Art\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 26.9\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C2778805511\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q1713\",\n" +
                "            \"display_name\": \"Citation\",\n" +
                "            \"level\": 2,\n" +
                "            \"score\": 26.9\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C86803240\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q420\",\n" +
                "            \"display_name\": \"Biology\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 25.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C124952713\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q8242\",\n" +
                "            \"display_name\": \"Literature\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 23.1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C151719136\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q3972943\",\n" +
                "            \"display_name\": \"Publishing\",\n" +
                "            \"level\": 2,\n" +
                "            \"score\": 23.1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C199360897\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q9143\",\n" +
                "            \"display_name\": \"Programming language\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 23.1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C124101348\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q172491\",\n" +
                "            \"display_name\": \"Data mining\",\n" +
                "            \"level\": 1,\n" +
                "            \"score\": 21.2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"https://openalex.org/C127413603\",\n" +
                "            \"wikidata\": \"https://www.wikidata.org/wiki/Q11023\",\n" +
                "            \"display_name\": \"Engineering\",\n" +
                "            \"level\": 0,\n" +
                "            \"score\": 21.2\n" +
                "        }\n" +
                "    ],\n" +
                "    \"counts_by_year\": [\n" +
                "        {\n" +
                "            \"year\": 2023,\n" +
                "            \"works_count\": 0,\n" +
                "            \"cited_by_count\": 167\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2022,\n" +
                "            \"works_count\": 2,\n" +
                "            \"cited_by_count\": 217\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2021,\n" +
                "            \"works_count\": 1,\n" +
                "            \"cited_by_count\": 226\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2020,\n" +
                "            \"works_count\": 3,\n" +
                "            \"cited_by_count\": 280\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2019,\n" +
                "            \"works_count\": 2,\n" +
                "            \"cited_by_count\": 235\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2018,\n" +
                "            \"works_count\": 3,\n" +
                "            \"cited_by_count\": 194\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2017,\n" +
                "            \"works_count\": 3,\n" +
                "            \"cited_by_count\": 167\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2016,\n" +
                "            \"works_count\": 2,\n" +
                "            \"cited_by_count\": 181\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2015,\n" +
                "            \"works_count\": 3,\n" +
                "            \"cited_by_count\": 216\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2014,\n" +
                "            \"works_count\": 4,\n" +
                "            \"cited_by_count\": 203\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2013,\n" +
                "            \"works_count\": 11,\n" +
                "            \"cited_by_count\": 155\n" +
                "        },\n" +
                "        {\n" +
                "            \"year\": 2012,\n" +
                "            \"works_count\": 11,\n" +
                "            \"cited_by_count\": 66\n" +
                "        }\n" +
                "    ],\n" +
                "    \"works_api_url\": \"https://api.openalex.org/works?filter=author.id:A5023888391\",\n" +
                "    \"updated_date\": \"2023-12-02T05:31:13.528388\",\n" +
                "    \"created_date\": \"2023-07-21\"\n" +
                "}";
        // 解析 JSON 字符串为 JSONObject
        JSONObject jsonObject = new JSONObject(jsonString);

        // 获取 x_concepts 数组
        JSONArray xConceptsArray = jsonObject.getJSONArray("x_concepts");

        // 创建一个列表来存储 display_name 的值
        List<String> displayNameList = new ArrayList<>();

        // 遍历数组并获取 display_name 属性
        for (int i = 0; i < xConceptsArray.length(); i++) {
            JSONObject conceptObject = xConceptsArray.getJSONObject(i);
            String displayName = conceptObject.getString("display_name");
            displayNameList.add(displayName);
        }

        // 将列表转换为 String 数组
        String[] displayNameArray = displayNameList.toArray(new String[0]);

        // 打印 String 数组中的值（可选）
        for (String name : displayNameArray) {
            System.out.println("Display Name: " + name);
        }
    }
}
