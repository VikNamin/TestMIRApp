package ru.vik.testmirapp

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vik.testmirapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // Значение открытого/закрытого меню
    private var isMenuOpen = false

    // Переменные ViewBinding
    private lateinit var _binding: ActivityMainBinding
    private val myBindClass get() = _binding

    // Массив иконок
    private val iconList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // Заполнение массива иконок
        iconList.add(R.drawable.ic_like)
        iconList.add(R.drawable.ic_lock)
        iconList.add(R.drawable.ic_menu)
        iconList.add(R.drawable.ic_mouse)
        iconList.add(R.drawable.ic_power)
        iconList.add(R.drawable.ic_refresh)

        val menuButton = myBindClass.menuButton
        val recyclerView = myBindClass.recyclerView

        // Инициализация RecyclerView
        val layoutManager = LinearLayoutManager(this)
        val adapter = ButtonAdapter(iconList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        // Подключение ItemTouchHelper-a для обработки свайпа
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Вешание слушателя нажатия на кнопку Меню
        menuButton.setOnClickListener {
            if (isMenuOpen) {
                // Закрыть меню
                val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                slideDown.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    // При окончании анимации -> скрыть RecyclerView
                    override fun onAnimationEnd(animation: Animation?) {
                        recyclerView.visibility = View.INVISIBLE
                    }
                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                recyclerView.startAnimation(slideDown)
                isMenuOpen = false
            }
            else {
                // Открыть меню
                val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
                slideUp.setAnimationListener(object : Animation.AnimationListener {
                    // На старте анимации -> показать RecyclerView
                    override fun onAnimationStart(animation: Animation?) {
                        recyclerView.visibility = View.VISIBLE
                    }
                    override fun onAnimationEnd(animation: Animation?) {}
                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                recyclerView.startAnimation(slideUp)
                isMenuOpen = true
            }
        }
    }
}