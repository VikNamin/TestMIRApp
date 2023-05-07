package ru.vik.testmirapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vik.testmirapp.databinding.ItemButtonRowBinding

class ButtonAdapter(private var iconList: MutableList<Int>) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemButtonRowBinding.inflate(inflater, parent, false)
        return ButtonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val currentIcon = iconList[position]
        with(holder.itemBinding){
            // Устанавливаем иконку для каждой кнопки
            button.setImageResource(currentIcon)
        }
    }

    // Метод удаления элемента из списка
    fun removeItem(position: Int) {
        iconList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    override fun getItemCount() = iconList.size

    class ButtonViewHolder(val itemBinding: ItemButtonRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {}
}