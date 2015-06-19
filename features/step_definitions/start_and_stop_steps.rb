# language: zh-CN

假如(/^用户启动秒表$/) do
    tap_mark 'Start'
end

当(/^经过(\d+)秒$/) do |second|
    sleep second.to_i + 0.2
end

那么(/^看见显示(\d+)秒$/) do |second|
    check_element_exists("* marked:'#{second}s'")
end


当(/^经过(\d+)秒，用户暂停秒表$/) do |second|
  step "经过#{second}秒"
  tap_mark 'Pause'
end

当(/^等待(\d+)秒$/) do |second|
    step "经过#{second}秒"
end

假如(/^秒表计时到(\d+)秒后暂停$/) do |second|
  step "用户启动秒表"
  step "经过#{second}秒，用户暂停秒表"
end

当(/^经过(\d+)秒，用户恢复计时$/) do |second|
  step "等待#{second}秒"
  step "用户启动秒表"
end