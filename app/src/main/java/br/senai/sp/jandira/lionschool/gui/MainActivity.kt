package br.senai.sp.jandira.lionschool.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.CoursesList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var curso by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Course>())
    }

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0,0,0))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            horizontalArrangement = Arrangement.Center

        )          {}

        Spacer(modifier = Modifier.height(height = 35.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = br.senai.sp.jandira.lionschool.R.drawable.logooo),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )
        }

        Spacer(modifier = Modifier.height(height = 35.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp)
        ) {

            Text(
                text = "Cursos:",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(height = 35.dp))

        // Chamada para a API

        val call = RetrofitFactory().getCourseService().getCourses()

        call.enqueue(object : Callback<CoursesList> {
            override fun onResponse(
                call: Call<CoursesList>, response: Response<CoursesList>
            ) {
                curso = response.body()!!.curso
            }

            override fun onFailure(call: Call<CoursesList>, t: Throwable) {
                Log.i("ds2m", "onFailure: ${t.message}")
            }

        })

        LazyColumn() {


            items(curso) {

                Log.i("Cursos", "${curso}: ")
//                Log.i("Alunos", "${alunos}: ")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(172.dp)
                        .padding(horizontal = 45.dp)
                        .padding(top = 15.dp)
                        .clickable {
                            val intent = Intent(context, TurmaActivity::class.java)
                            intent.putExtra("siglaCurso", it.sigla)
                            intent.putExtra("nomeCurso", it.nome)
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp,
                    ),
                    backgroundColor = Color(140,82,255)
                ) {

                    Row(
                        modifier = Modifier.padding(start = 20.dp)


                    ) {
                        Text(
                            text = it.sigla,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )

                    }
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LionSchoolTheme {
        Greeting("Android")
    }
}