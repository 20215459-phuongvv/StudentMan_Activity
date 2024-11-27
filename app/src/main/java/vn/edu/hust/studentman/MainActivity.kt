package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

  private lateinit var students: MutableList<StudentModel>
  private lateinit var adapter: ArrayAdapter<StudentModel>
  private lateinit var listView: ListView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    listView = findViewById(R.id.list_view_students)
    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    listView.adapter = adapter

    registerForContextMenu(listView)
    }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        val intent = Intent(this, StudentActivity::class.java)
        startActivityForResult(intent, ADD_STUDENT)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.menu_context, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val student = students[info.position]

    when (item.itemId) {
      R.id.menu_edit -> {
        val intent = Intent(this, StudentActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", info.position)
        startActivityForResult(intent, EDIT_STUDENT)
        return true
      }
      R.id.menu_remove -> {
        students.removeAt(info.position)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "${student.studentName} đã bị xóa", Toast.LENGTH_SHORT).show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      val student = data.getParcelableExtra<StudentModel>("student")!!
      when (requestCode) {
        ADD_STUDENT -> {
          students.add(student)
        }
        EDIT_STUDENT -> {
          val position = data.getIntExtra("position", -1)
          if (position != -1) {
            students[position] = student
          }
        }
      }
      adapter.notifyDataSetChanged()
    }
  }

  companion object {
    const val ADD_STUDENT = 1
    const val EDIT_STUDENT = 2
  }
}