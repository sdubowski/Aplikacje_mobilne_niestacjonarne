package com.example.remembereverything

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.remembereverything.model.Task
import com.example.remembereverything.model.TaskStatusEnum
import com.example.remembereverything.repository.TaskRepository
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    var doneRef = TaskRepository.databaseRef

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doneRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val pieChart = findViewById<PieChart>(R.id.any_chart_view)
                val tasks: MutableList<PieEntry> = ArrayList()
                val items: ArrayList<Task> = ArrayList()
                data.children.forEach { items.add(it.getValue(Task::class.java)!!) }
                val allCount = data.childrenCount.toInt()
                val doneTasks = items.filter { task -> task.taskStatus == TaskStatusEnum.Done }
                val toDoTasks = items.filter { task -> task.taskStatus == TaskStatusEnum.ToDo }
                var x = 0
                var y = 0
                if (allCount != 0) {
                    x = (doneTasks.size) * 100 / allCount
                    y = (toDoTasks.size) * 100 / allCount
                }
                tasks.add(PieEntry(x.toFloat(), "Done"))
                tasks.add(PieEntry(y.toFloat(), "To do"))

                val pieDataSet = PieDataSet(tasks, "tasks in %")
                pieDataSet.setColors(ColorTemplate.PASTEL_COLORS.toMutableList())
                val colorList: MutableList<Int> = ArrayList()
                colorList.add(Color.GRAY)
                pieDataSet.setValueTextColors(colorList)
                pieDataSet.setValueTextSize(16f)

                val pieData = PieData(pieDataSet)
                pieChart.setData(pieData)
                pieChart.getDescription().setEnabled(false)
                pieChart.setCenterText("Complete everything")
                pieChart.animate()


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })


        val button = findViewById<Button>(R.id.menu_button)
        button.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

}
