package com.kotlin.calculatormvp.ui.calculatorScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kotlin.calculatormvp.R
import com.kotlin.calculatormvp.calculatorElements.CalculatorElement
import com.kotlin.calculatormvp.calculatorElements.CalculatorOtherElement
import com.kotlin.calculatormvp.di.component.DaggerFragmentComponent
import com.kotlin.calculatormvp.di.module.FragmentModule
import com.kotlin.calculatormvp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_calculator.*
import javax.inject.Inject

class CalculatorFragment : Fragment(), CalculatorContract.View {

    companion object {
        val TAG = "CalculatorFragment"
        fun newInstance(): CalculatorFragment {
            return CalculatorFragment()
        }
    }

    @Inject
    lateinit var presenter: CalculatorContract.Presenter

    private var calculatorAdapter: CalculatorSubviewsAdapter? = null
    private var historyAdapter: CalculationsHistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.createCalculator()
        initGrid()
    }


    private fun initGrid() {
        val gridObserver = fragmentCalculatorCalculatorGridView.viewTreeObserver
        gridObserver.addOnDrawListener {
            calculatorAdapter?.getAdapterList()?.filterIsInstance<CalculatorOtherElement>()
                ?.forEach {
                    when (it) {
                        CalculatorOtherElement.EQUALS -> it.action = {
                            presenter.computePressed()
                        }
                        CalculatorOtherElement.CLEAR -> it.action = {
                            presenter.clearExpressionPressed()
                        }
                        CalculatorOtherElement.BACKSPACE -> it.action = {
                            presenter.backspacePressed()
                        }

                    }
                }
        }
    }

    private fun injectDependency() {
        DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)
    }

    /* implementation of CalculatorContract.View interface below */

    override fun showCalculator(elements: ArrayList<CalculatorElement>) {
        calculatorAdapter = activity?.let { CalculatorSubviewsAdapter(elements, it) }
        fragmentCalculatorCalculatorGridView.adapter = calculatorAdapter
        fragmentCalculatorCalculatorGridView.invalidate()
        calculatorAdapter?.notifyDataSetChanged()

        fragmentCalculatorCalculatorGridView.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    // Get the GridView selected/clicked item text
                    val selectedItem = calculatorAdapter?.getItem(position)
                    if (selectedItem is CalculatorOtherElement) {
                        selectedItem.action()
                    } else {
                        selectedItem?.let { presenter.addElementToExpressionList(it) }
                    }
                }
            }
    }

    override fun drawExpression(expressionStr: String) {
        fragmentCalculatorExpressionTV.text = expressionStr
    }

    override fun drawHistory(historyList: MutableList<String>) {
        initHistory(historyList)
        fragmentCalculatorHistoryRV.adapter = historyAdapter
    }

    private fun initHistory(historyList: MutableList<String>) {
        if (historyAdapter == null) {
            historyAdapter = CalculationsHistoryAdapter(historyList, this)
            val llm = LinearLayoutManager(context)
            llm.stackFromEnd = true     // items gravity sticks to bottom
            llm.reverseLayout = false   // item list sorting (new messages start from the bottom)
            fragmentCalculatorHistoryRV.layoutManager = llm
        }else{
            historyAdapter?.setAdapterData(historyList)
        }
    }



    override fun showSnack(msg: String) {
        val snack = Snackbar.make(fragmentCalculatorCalculatorGridView, msg, Snackbar.LENGTH_LONG)
        snack.show()
    }


}
