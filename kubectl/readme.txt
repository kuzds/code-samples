кладем kubectl и настраиваем на него Path
кладем папки в user home

kubectl config get-contexts
kubectl config current-context

kubectl get pods -n test
в linux-среде
kubectl get pods -n test | grep j-tpsbp-out

kubectl delete pod <POD_NAME>

kubectl logs <POD_NAME> -n test --tail 1000

kubectl get nodes -o wide -n test